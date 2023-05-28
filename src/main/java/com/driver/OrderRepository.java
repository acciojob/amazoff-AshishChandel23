package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();

    private Map<String, String> orderPartnerMap = new HashMap<>();
    private Map<String, ArrayList<String>> partnerOrderMap = new HashMap<>();

    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerMap.put(partner.getId(), partner);
    }

    public Optional<Order> getOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

    public void addOrderPartnerById(String orderId, String partnerId) {
        ArrayList<String> orders = partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
        orders.add(orderId);
        partnerOrderMap.put(partnerId, orders);
        orderPartnerMap.put(orderId, partnerId);
    }

    public List<String> getAllOrdersForPartner(String partnerId) {
        return partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public List<String> getAssignedOrders() {
        return new ArrayList<>(orderPartnerMap.keySet());
    }


    public List<String> getOrdersForPartner(String partnerId){
        return partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
    }
    public Optional<String> getPartnerForOrder(String orderId){
        if(orderPartnerMap.containsKey(orderId)){
            return Optional.of(orderPartnerMap.get(orderId));
        }
        return Optional.empty();
    }

    public void removeOrderPartnerMapping(String orderId) {

        orderPartnerMap.remove(orderId);
    }

    public void deletePartner(String partnerId) {
        partnerMap.remove(partnerId);
        partnerOrderMap.remove(partnerId);
    }
    public void deleteOrder(String orderId){
        orderMap.remove(orderId);
        orderPartnerMap.remove(orderId);
    }
}
