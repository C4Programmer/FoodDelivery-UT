import { Category } from "./category";
import { Restaurant } from "./restaurant";

export class Menu{
    public id: number | null;
    public name: string | null;
    public description: string | null;
    public price: number | null;
    public category: Category;

    constructor(){
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.category = new Category();
    }
}