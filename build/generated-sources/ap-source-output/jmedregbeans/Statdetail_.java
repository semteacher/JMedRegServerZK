package jmedregbeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jmedregbeans.Medoffice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-24T17:47:34")
@StaticMetamodel(Statdetail.class)
public class Statdetail_ { 

    public static volatile SingularAttribute<Statdetail, Integer> id;
    public static volatile SingularAttribute<Statdetail, Medoffice> officeId;
    public static volatile SingularAttribute<Statdetail, Date> vizitDate;
    public static volatile SingularAttribute<Statdetail, Integer> patientCount;

}