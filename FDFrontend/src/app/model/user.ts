import { Administrator } from "./administrator";
import { Customer } from "./customer";
import { UserType } from "./userType";

export class User{
    public id: number | null;
    public username: string | null;
    public password: string | null;
    public firstName: string | null;
    public lastName: string | null;
    public email: string | null;
    public phone: string | null;

    public userTypeId : number | null;

    public userType: UserType;
    public administrator: Administrator;
    public customer: Customer;

    constructor(){
        this.id = null;
        this.username = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
        this.userType = new UserType();
        this.administrator = new Administrator();
        this.customer = new Customer();
        this.userTypeId = null;
    }
}