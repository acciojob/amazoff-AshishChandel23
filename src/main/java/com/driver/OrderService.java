package com.driver;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class OrderService {

    @Autowired
    private OrderRepository repository;
    public void addOrder(Order order) {
        repository.addOrder(order);
        log.info("Adding order with order id : "+order.getId());
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        repository.addPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) throws RuntimeException{
        Optional<Order> orderOptional = repository.getOrderById(orderId);
        Optional<DeliveryPartner> partnerOptional = repository.getPartnerById(partnerId);
        if(orderOptional.isEmpty()){
            log.warn("Order is not present : "+orderId);
            throw new RuntimeException("Order is not present : "+orderId);
        }
        if(partnerOptional.isEmpty()){
            log.warn("Partner is not present : "+partnerId);
            throw new RuntimeException("Partner is not present : "+partnerId);
        }
        DeliveryPartner partner = partnerOptional.get();
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
        repository.addPartner(partner);
        repository.addOrderPartnerById(orderId,partnerId);
    }

    public Order getOrderById(String orderId) throws RuntimeException{
        Optional<Order> orderOptional = repository.getOrderById(orderId);
        if(orderOptional.isEmpty()){
            log.warn("Order is not present : "+orderId);
            throw new RuntimeException("Order is not present : "+orderId);
        }
        return orderOptional.get();
    }
    public Integer getOrderCountForPartner(String partnerId){
        Optional<DeliveryPartner> p = repository.getPartnerById(partnerId);
        if(p.isPresent()){
            return p.get().getNumberOfOrders();
        }
        return 0;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        Optional<DeliveryPartner> partnerOptional = repository.getPartnerById(partnerId);
        if(partnerOptional.isEmpty()){
            log.warn("Partner is not present : "+partnerId);
            throw new RuntimeException("Partner is not present : "+partnerId);
        }
        return partnerOptional.get();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return repository.getAllOrdersForPartner(partnerId);
    }

    public List<String> getAllOrders() {
        return repository.getAllOrders();
    }

    public Integer getUnassignedOrders() {
        return repository.getAllOrders().size() - repository.getAssignedOrders().size();
    }

    public Integer getOrdersLeftForPartnerAfterTime(String time, String partnerId){
        List<String> orderIds = repository.getAllOrdersForPartner(partnerId);
        int currTime =TimeUtils.convertTime(time);
        int orderLeft=0;
        for(String orderId : orderIds){
            int deliveryTime = repository.getOrderById(orderId).get().getDeliveryTime();
            if(currTime<deliveryTime){
                orderLeft++;
            }
        }
        return orderLeft;
    }

    public String getLastDeliveryTimeForPartner(String partnerId){
        List<String> orderIds = repository.getAllOrdersForPartner(partnerId);
        int max =0;
        for(String orderId : orderIds){
            int deliveryTime = repository.getOrderById(orderId).get().getDeliveryTime();
            if(max<deliveryTime){
                max=deliveryTime;
            }
        }
        return TimeUtils.convertTime(max);

    }
    public void deletePartner(String partnerId){
        List<String> orders = repository.getOrdersForPartner(partnerId);
        repository.deletePartner(partnerId);
        for(String orderId : orders){
            repository.removeOrderPartnerMapping(orderId);
        }
    }

    public void deleteOrder(String orderId) {
        Optional<String> partnerIdOpt = repository.getPartnerForOrder(orderId);

        repository.deleteOrder((orderId));
        if(partnerIdOpt.isPresent()){
            List<String> orderIds = repository.getAllOrdersForPartner(partnerIdOpt.get());
            orderIds.remove(orderId);
        }
    }
}
