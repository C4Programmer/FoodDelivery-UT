import { Administrator } from "./administrator";
import { DeliveryZone } from "./deliveryZone";
import { Menu } from "./menu";

export class Restaurant{
    public id: number | null;
    public name: string | null;
    public location: string | null;
    public administrator: Administrator;
    public menus: Menu[] | null;
    public deliveryZones: DeliveryZone[] | null;

    constructor(){
        this.id = null;
        this.name = null;
        this.location = null;
        this.menus = [];
        this.deliveryZones = [];
        this.administrator = new Administrator();
    }
}