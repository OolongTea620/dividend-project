package zerobase.devidend.persist.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.devidend.model.Dividend;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"companyId", "date"}
        )
    }
)
/**
 * Unique Key
 * 중복 데이터 저장을 방지하는 제약조건
 * 단일 컬럼 뿐 아니라 복합 컬럼을 지정할 수도 있음
 */

public class DividendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity (Long companyId, Dividend dividend) {
        this.companyId = companyId;
        this.date = dividend.getDate();
        this.dividend = dividend.getDividend();
    }
}
