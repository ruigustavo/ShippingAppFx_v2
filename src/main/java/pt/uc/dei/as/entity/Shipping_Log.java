/*
 * Copyright 2017 (C) <University of Coimbra>
 *
 * Created on : 15-02-2017
 * Author     : Bruno Cabral
 */
package pt.uc.dei.as.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The persistent class for the Workers_log database table.
 *
 */
@Entity
@Table(name = "Shipping_Log")
@NamedQueries({ @NamedQuery(name = "Shipping_Log.findAll", query = "SELECT c FROM Shipping_Log c"),
        @NamedQuery(name = "Shipping_Log.findWorker", query = "SELECT c FROM Shipping_Log AS c where c.workers_Name = :workers_name"), })
public class Shipping_Log implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idLog;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date shipping_Date;

    @Column(nullable = false, length = 100)
    private String workers_Name;

    @Column(nullable = false)
    private int idOrders;

    // bi-directional many-to-one association to Order
    //@OneToMany(mappedBy = "worker")
    //private List<Order> orders;

    public Shipping_Log() {
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public Date getShipping_Date() {
        return this.shipping_Date;
    }

    public StringProperty shipping_DateProperty() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm:");
        String date1 = format1.format(this.shipping_Date);
        return new SimpleStringProperty(date1);
    }

    public void setShipping_Date(Date shipping_Date) {
        this.shipping_Date = shipping_Date;
    }

    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    public String getWorkers_Name() {
        return this.workers_Name;
    }

    public StringProperty workers_NameProperty() {
        return new SimpleStringProperty(this.workers_Name);
    }

    public void setWorkers_Name(String workers_Name) {
        this.workers_Name = workers_Name;
    }


    ///TODO ALTERAR ORDER PARA TER TBM QUEM A FEZ -- LOGGING STUFF
	/*public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setClient(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setClient(null);

		return order;
	}*/

}