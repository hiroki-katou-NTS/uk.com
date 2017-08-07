module nts.uk.com.view.cps005.a {
    export module service {
        export class Service {
            paths = {
                getAllPerInfoCtg: "ctx/bs/person/person/info/category/findby/company",
                getPerInfoCtg: "ctx/bs/person/person/info/category/findby/{0}",
                getPerInfoCtgWithItemsName: "ctx/bs/person/person/info/category/find/withItemsName/{0}",
//                getAllPerInfoItemDefByCtgId: "ctx/bs/person/person/info/ctgItem/findby/categoryId/{0}",
//                getPerInfoItemDefById: "ctx/bs/person/person/info/ctgItem/findby/itemId/{0}",
//                getPerInfoItemDefByListId: "ctx/bs/person/person/info/ctgItem/findby/listItemId",
            }
            constructor() {

            }

            getAllPerInfoCtg(): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.getAllPerInfoCtg);
            };

            getPerInfoCtg(categoryId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getPerInfoCtg, categoryId);
                return nts.uk.request.ajax("com", _path);
            };
            
            getPerInfoCtgWithItemsName(categoryId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getPerInfoCtgWithItemsName, categoryId);
                return nts.uk.request.ajax("com", _path);
            };
            
            
//             getAllPerInfoItemDefByCtgId(perInfoCtgId: string): JQueryPromise<any> {
//                let _path = nts.uk.text.format(this.paths.getAllPerInfoItemDefByCtgId, perInfoCtgId);
//                return nts.uk.request.ajax("com", _path);
//            };
//            
//            getPerInfoItemDefById(perInfoItemId: string): JQueryPromise<any> {
//                let _path = nts.uk.text.format(this.paths.getPerInfoItemDefById, perInfoItemId);
//                return nts.uk.request.ajax("com", _path);
//            };
//            
//             getPerInfoItemDefByListId(): JQueryPromise<any> {
//                return nts.uk.request.ajax("com", this.paths.getPerInfoItemDefByListId, ["0656D147-0C9A-4DBB-A069-8C355EF8C1F9", "1A62BAEC-7646-4C45-8833-5034D7B48457", "2C1DC4C6-0C0A-4491-8983-59F8939D0E7C","39A2CA87-C6D6-4F01-BF30-78C454EF97B5"]);
//            };
        }
    }
}
