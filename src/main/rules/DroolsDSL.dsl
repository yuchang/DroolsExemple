[consequence][]Set age of customer to {value}=c.setAge({value});
[consequence][]View details of customer=System.out.println(c.toString());
[condition][]There is a Customer with name of "{value}"=c: Customer (name =="{value}")
[condition][]Customer is at least {number} and gender is "{value}"=Customer(age >= {number}, gender=="{value}")
[consequence][]Log : "{message}"=System.out.println("{message}");
[consequence][]Set name of customer to "{value}"=c.setName("{value}");
[consequence][]Create customer : "{value}"=insert(new Customer("{value}"));
[condition][]There is no current Customer with name : "{value}"=not Customer(name == "{value}")
[consequence][]Report error : "{error}"=System.err.println("{error}");
[consequence][]Retract the fact : '{variable}'=retract({variable}); //this would retract bound variable {variable}
