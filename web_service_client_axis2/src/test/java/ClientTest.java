import com.google.common.collect.Sets;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.commons.codec.binary.Base64;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts.ArrayOfOrderItem;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts.DeliveryMethod;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts.OrderItem;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts.ProductType;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts_common.Message;
import org.datacontract.schemas._2004._07.ukho_b2b_contracts_messages.SubmitOrderRequest;
import org.junit.Test;
import uk.gov.ukho.enavigator._1_0.Order;
import uk.gov.ukho.enavigator._1_0.OrderingServiceStub;
import uk.gov.ukho.enavigator._1_0.SubmitOrder;
import uk.gov.ukho.enavigator._1_0.SubmitOrderResponse;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class ClientTest {
    public static final String USERNAME = "B2BWuxi";
    public static final String PASSWORD = "Rm2q2OUW";

    /**
     * 目前只做 AVCS_CHARTS
     *
     * @param productId 必须；产品ID
     * @return
     */
    public static OrderItem makeOrderItem(String productId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("");

        orderItem.setProductPeriod(12);
        orderItem.setProductType(ProductType.AVCSCharts);
        orderItem.setQuantity(0);
        return orderItem;
    }


    /**
     * The quantity of product required. Mandatory for “paper” products. Not required for other products.
     *
     * @param orderItems 必须；
     * @return
     */
    public static ArrayOfOrderItem makeOrderItemPackage(Set<OrderItem> orderItems) {
        ArrayOfOrderItem orderItemPackage = new ArrayOfOrderItem();
        for (OrderItem orderItem : orderItems) {
            orderItemPackage.addOrderItem(orderItem);
        }
        return orderItemPackage;
    }

    /**
     * 所需参数都为必须
     *
     * @param distId     必须；User Management > Companies > Company type: Distributors
     * @param licenceId  必须；45039 -> Non-IMO Vessel
     * @param orderItems 必须；
     * @return
     */
    public static SubmitOrderRequest makeSubmitOrderRequest(int distId, int licenceId, ArrayOfOrderItem orderItems) {
        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();
        Order order = new Order();
        order.setDistId(distId);
        order.setLicenceId(licenceId);
        order.setDeliveryMethod(DeliveryMethod.Both);
        order.setOrderItems(orderItems);
        submitOrderRequest.setOrder(order);
        submitOrderRequest.setRequestId(UUID.randomUUID().toString());
        return submitOrderRequest;
    }

    @Test
    public void invoke1() throws RemoteException {
        OrderingServiceStub orderingServiceStub = new OrderingServiceStub();
//        orderingServiceStub._getServiceClient().getServiceContext().setProperty("ws-security.username", USERNAME);
//        orderingServiceStub._getServiceClient().getServiceContext().setProperty("ws-security.password", PASSWORD);
//        orderingServiceStub._getServiceClient().getOptions().setProperty("ws-security.username", USERNAME);
//        orderingServiceStub._getServiceClient().getOptions().setProperty("ws-security.password", PASSWORD);
        OrderItem orderItem = makeOrderItem("AR201130");
        ArrayOfOrderItem orderItemPackage = makeOrderItemPackage(Sets.newHashSet(orderItem));
        SubmitOrderRequest request = makeSubmitOrderRequest(6644, 45039, orderItemPackage);

        SubmitOrder submitOrder = new SubmitOrder();
        submitOrder.setRequest(request);
        SubmitOrderResponse response = orderingServiceStub.submitOrder(submitOrder);
        for (Message message : response.getSubmitOrderResult().getMessages().getMessage()) {
            System.out.println(message.getMessageText());
        }
    }

}
