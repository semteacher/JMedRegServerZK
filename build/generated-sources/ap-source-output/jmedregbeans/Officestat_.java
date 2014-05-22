package jmedregbeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jmedregbeans.Medoffice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-22T18:59:15")
@StaticMetamodel(Officestat.class)
public class Officestat_ { 

    public static volatile SingularAttribute<Officestat, Integer> id;
    public static volatile SingularAttribute<Officestat, Medoffice> officeId;
    public static volatile SingularAttribute<Officestat, String> staffFio;
    public static volatile SingularAttribute<Officestat, Integer> patientTotal;
    public static volatile SingularAttribute<Officestat, Date> statDate;
    public static volatile SingularAttribute<Officestat, String> updRecord;

}