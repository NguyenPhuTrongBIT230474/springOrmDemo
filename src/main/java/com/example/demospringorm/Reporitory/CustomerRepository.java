package com.example.demospringorm.Reporitory;

import com.example.demospringorm.Entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    List<Customer> findByName(String name);

    List<Customer> findByAgeGreaterThan(int age);

    List<Customer> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String nameKeyword, String emailKeyword);

    List<Customer> findTop5ByOrderByAgeAsc();

    boolean existsBySDT(String SDT);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmailJPQL(@Param("email")String email);

    @Query("SELECT c FROM Customer c WHERE c.age BETWEEN :minAge AND :maxAge")
    List<Customer> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    @Query("SELECT c.name, c.age FROM Customer c")
    List<Object[]> findNameAndAge();


    @Query("SELECT COUNT(c) FROM Customer c WHERE c.SDT LIKE '01%'")
    Long countCustomersSDTStartWith01();

    @Query("SELECT c FROM Customer c ORDER BY c.age DESC")
    List<Customer> findAllCustomerAgeOrderedDESC();

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.email = :newEmail WHERE c.id = :id")
    int updateEmailById(@Param("id") Long id, @Param("newEmail") String newEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c WHERE c.age < :ageLimit")
    int deleteCustomersByAgeLessThan(@Param("ageLimit") int ageLimit);

    @Query(value = "SELECT * FROM customer WHERE name LIKE '%:keyword%'", nativeQuery = true)
    List<Customer> searchByKeywordNameNative(@Param("keyword") String keyword);

    @Query("SELECT c FROM Customer c WHERE c.id IN :ids")
    List<Customer> findByIdsInList(@Param("ids") List<Long> ids);


    @Query("SELECT c FROM Customer c WHERE c.id != :id")
    List<Customer> findAllExceptId(@Param("id") Long id);

    @Query("SELECT c.age, COUNT(c) FROM Customer c GROUP BY c.age HAVING COUNT(c) > 1")
    List<Object[]> countCustomersByAgeHavingDuplicates();

    @Query("SELECT MIN(c.age) FROM Customer c")
    Integer findMinAge();

    @Query("SELECT AVG(c.age) FROM Customer c")
    Double findAverageAge();

    @Query("SELECT c.age, COUNT(c) FROM Customer c GROUP BY c.age")
    List<Object[]> countCustomersByAge();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.email = :email")
    boolean existsByEmailQuery(@Param("email") String email);

    @Query("SELECT c.email FROM Customer c WHERE c.name = :name AND c.email IS NOT NULL")
    List<String> findEmailsByName(@Param("name") String name);
}