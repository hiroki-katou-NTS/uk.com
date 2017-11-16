module nts.uk.com.view.cps005.b {
    export module service {
        export class Service {
            paths = {
                getAllPerInfoItemDefByCtgId: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}",
                getPerInfoItemDefById: "ctx/bs/person/info/ctgItem/findby/itemId/{0}",
                addItemDef: "ctx/bs/person/info/ctgItem/add",
                updateItemDef: "ctx/bs/person/info/ctgItem/update",
                removeItemDef: "ctx/bs/person/info/ctgItem/remove",
                
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
            
            addItemDef(newItemDef: any): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.addItemDef, newItemDef);
            };
            
            updateItemDef(newItemDef: any): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.updateItemDef, newItemDef);
            };
            
            removeItemDef(removeCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.removeItemDef, removeCommand);
            };
        }
    }
}
