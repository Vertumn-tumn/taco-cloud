package tacos.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>, PagingAndSortingRepository<Taco,Long> {
    Page<Taco> findAll(Pageable pageable);
}
