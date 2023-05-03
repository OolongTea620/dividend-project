package zerobase.devidend.persist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.devidend.persist.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>{
    boolean existsAllByTicker(String ticker);

    Optional<CompanyEntity> findByName(String name);

}
