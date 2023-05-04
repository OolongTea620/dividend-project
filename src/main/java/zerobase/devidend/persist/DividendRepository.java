package zerobase.devidend.persist;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.devidend.persist.entity.DividendEntity;

import javax.transaction.Transactional;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findAllByCompanyId(Long companyId);

    @Transactional
    void deleteAllByCompanyId(Long id);
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);


}
