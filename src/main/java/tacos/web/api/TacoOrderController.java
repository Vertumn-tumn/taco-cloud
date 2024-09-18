package tacos.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tacos.TacoOrder;
import tacos.data.OrderRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoOrderController {
    private OrderRepository orderRepo;

    public TacoOrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Transactional
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> putOrder(@PathVariable Long id, @RequestBody TacoOrder order) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id);
        orderRepo.save(order);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> patchOrder(@PathVariable Long id, @RequestBody TacoOrder patch) {
        Optional<TacoOrder> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        TacoOrder tacoOrder = order.get();
        if (patch.getDeliveryName() != null) {
            tacoOrder.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            tacoOrder.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            tacoOrder.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            tacoOrder.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            tacoOrder.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            tacoOrder.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            tacoOrder.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            tacoOrder.setCcCVV(patch.getCcCVV());
        }
        if (!patch.getTacos().isEmpty()) {
            tacoOrder.setTacos(patch.getTacos());
        }
        orderRepo.save(tacoOrder);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
