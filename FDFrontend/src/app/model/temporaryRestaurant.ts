export class TemporaryRestaurant{
    public name: string | null;
    public location: string | null;
    public administratorId: number | null;
    
    public deliveryZone1Name : string | null;
    public deliveryZone1Location : string | null;
    public deliveryZone2Name : string | null;
    public deliveryZone2Location : string | null;
    public deliveryZone3Name : string | null;
    public deliveryZone3Location : string | null;

    public menu1Name: string | null;
    public menu1Description: string | null;
    public menu1Price: number | null;
    public menu1Category: number | null;

    public menu2Name: string | null;
    public menu2Description: string | null;
    public menu2Price: number | null;
    public menu2Category: number | null;

    public menu3Name: string | null;
    public menu3Description: string | null;
    public menu3Price: number | null;
    public menu3Category: number | null;

    constructor(){
    this.name=null;
    this.location=null;
    this.administratorId=null;
    
    this.deliveryZone1Name=null;
    this.deliveryZone1Location=null;
    this.deliveryZone2Name=null;
    this.deliveryZone2Location=null;
    this.deliveryZone3Name=null;
    this.deliveryZone3Location=null;

    this.menu1Name=null;
    this.menu1Description=null;
    this.menu1Price=null;
    this.menu1Category=null;

    this.menu2Name=null;
    this.menu2Description=null;
    this.menu2Price=null;
    this.menu2Category=null;

    this.menu3Name=null;
    this.menu3Description=null;
    this.menu3Price=null;
    this.menu3Category=null;
    }
}