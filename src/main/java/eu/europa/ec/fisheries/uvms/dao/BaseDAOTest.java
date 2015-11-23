package eu.europa.ec.fisheries.uvms.dao;

import com.ninja_squad.dbsetup.DbSetupTracker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

@Slf4j
public abstract class BaseDAOTest {

    private final String TEST_DB_USER = "sa";
    private final String TEST_DB_PASSWORD = StringUtils.EMPTY;
    private final String TEST_DB_URL = "jdbc:h2:mem:testdb;INIT=CREATE SCHEMA IF NOT EXISTS "+ getSchema() +";DATABASE_TO_UPPER=false;TRACE_LEVEL_SYSTEM_OUT=2";

    protected DbSetupTracker dbSetupTracker = new DbSetupTracker();

    protected DataSource ds = JdbcConnectionPool.create(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASSWORD);

    private EntityManagerFactory emFactory;

    protected EntityManager em;

    public BaseDAOTest() {

        log.info("BuildingEntityManager for unit tests");
        emFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
        em = emFactory.createEntityManager();
    }

    protected abstract String getSchema();

    protected abstract String getPersistenceUnitName();
}