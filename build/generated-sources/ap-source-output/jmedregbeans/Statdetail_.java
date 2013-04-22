package jmedregbeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jmedregbeans.Medoffice;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-03T14:33:23")
@StaticMetamodel(Statdetail.class)
public class Statdetail_ { 

    public static volatile SingularAttribute<Statdetail, Integer> id;
    public static volatile SingularAttribute<Statdetail, Medoffice> officeId;
    public static volatile SingularAttribute<Statdetail, Date> vizitDate;
    public static volatile SingularAttribute<Statdetail, Integer> patientCount;

}