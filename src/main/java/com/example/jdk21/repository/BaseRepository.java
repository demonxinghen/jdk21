package com.example.jdk21.repository;

import com.example.jdk21.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author admin
 * @date 2023/12/27 17:02
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseModel, ID> extends JpaRepository<T, ID> {

    @Query("update #{#entityName} set deleted = true where id = :id and deleted = false")
    @Transactional
    @Modifying
    @Override
    void deleteById(@Param("id") ID id);

    @Query("update #{#entityName} set deleted = true where id in :ids and deleted = false")
    @Transactional
    @Modifying
    @Override
    void deleteAllById(@Param("ids") Iterable<? extends ID> ids);

    // @Query("update #{#entityName} set deleted = 1 where id = #{entity.id} and deleted = false")
    // @Transactional
    // @Modifying
    // @Override
    // void delete(@Param("entity") T entity);
}
