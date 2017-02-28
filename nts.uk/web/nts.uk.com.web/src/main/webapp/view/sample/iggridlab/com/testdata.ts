module testdata {

    export class HogeItem {
        code: KnockoutObservable<String>;
        name: KnockoutObservable<String>;
        flag: KnockoutObservable<boolean>;
        
        constructor(code: String, name: String) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.flag = ko.observable(true);
        }
    }
    
    export function createHogeArray(numberOfItems: number) {
        let array = [];
        for (let i = 0; i < numberOfItems; i++) {
            array.push(new HogeItem("" + i, "ほげー" + i));
        }
        
        return array;
    }
    
    export function createRandomHogeArray(numberOfItems: number) {
        let array = [];
        for (let i = 0; i < numberOfItems; i++) {
            array.push(new HogeItem("" + i, "ほげー" + Math.random()));
        }
        
        return array;
    }
}
