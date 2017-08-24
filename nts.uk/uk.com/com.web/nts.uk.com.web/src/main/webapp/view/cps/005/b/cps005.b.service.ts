module nts.uk.com.view.cps005.b {
    export module service {
        export class Service {
            paths = {
                getAllPerInfoItemDefByCtgId: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}",
                getPerInfoItemDefById: "ctx/bs/person/info/ctgItem/findby/itemId/{0}",
            }

            constructor() {

            }

            getAllPerInfoItemDefByCtgId(categoryId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAllPerInfoItemDefByCtgId, categoryId);
                return nts.uk.request.ajax("com", _path);
            };
            
            getPerInfoItemDefById(itemId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getPerInfoItemDefById, itemId);
                return nts.uk.request.ajax("com", _path);
            };

        }
    }
}
