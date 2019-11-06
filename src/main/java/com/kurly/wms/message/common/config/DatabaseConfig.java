package com.kurly.wms.message.common.config;


import com.kurly.wms.message.common.config.properties.DatabaseProperties;
import com.kurly.wms.message.common.config.properties.DefaultDatabaseProperties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


public abstract class DatabaseConfig {

    public static final String BASE_PACKAGE = "com.kurly.wms.message.repository";
    public static final String TYPE_ALIASES_PACKAGE = "com.kurly.wms.message.domain";
    public static final String MAPPER_LOCATIONS_PATH = "classpath:mapper/**/*.xml";

    @Bean
    public abstract DataSource dataSource();

    protected void configureDataSource(BasicDataSource dataSource, DatabaseProperties databaseProperties) {

        dataSource.setDriverClassName(databaseProperties.getDriverClassName());
        dataSource.setUrl(databaseProperties.getUrl());
        dataSource.setUsername(databaseProperties.getUserName());
        dataSource.setPassword(databaseProperties.getPassword());

        dataSource.setMaxTotal(databaseProperties.getMaxActive());
        dataSource.setMaxIdle(databaseProperties.getMaxIdle());
        dataSource.setMinIdle(databaseProperties.getMinIdle());
//        dataSource.setMaxWait(databaseProperties.getMaxWait());

        // 커넥션 풀에서 커넥션을 얻어올 때 테스트 실행(기본값: true)
        dataSource.setTestOnBorrow(false);

        // 커넥션 풀로 커넥션을 반환할 때 테스트 실행(기본값: false)
        dataSource.setTestOnReturn(false);

        // Evictor 스레드가 실행될 때 (timeBetweenEvictionRunMillis > 0) 커넥션 풀 안에 있는 유휴 상태의 커넥션을 대상으로 테스트 실행(기본값: false )
        dataSource.setTestWhileIdle ( true );

        dataSource.setValidationQuery ("SELECT 1 FROM DUAL");

        // Evictor 스레드 동작 시 커넥션의 유휴 시간을 확인해 설정 값 이상일 경우 커넥션을 제거한다(기본값: 30분)
        //dataSource.setMinEvictableIdleTimeMillis ( -1);

        // Evictor 스레드가 동작하는 간격. 기본값은 -1이며 Evictor 스레드의 실행이 비활성화돼 있다.
        dataSource.setTimeBetweenEvictionRunsMillis (databaseProperties.getTimeBetweenEvictionRunsMillis());

    }

}

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DefaultDatabaseProperties.class)
@MapperScan(basePackages = DatabaseConfig.BASE_PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
class DefaultDatabaseConfig extends DatabaseConfig{

    @Autowired
    private DefaultDatabaseProperties defaultDatabaseProperties;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        configureDataSource(dataSource, defaultDatabaseProperties);

        return dataSource;
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource){

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager (dataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        transactionManager.setNestedTransactionAllowed(true);

        return transactionManager;
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean ();

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);

        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
        sqlSessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(false);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType( ExecutorType.REUSE);
        configuration.setLazyLoadingEnabled(false);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }



}


