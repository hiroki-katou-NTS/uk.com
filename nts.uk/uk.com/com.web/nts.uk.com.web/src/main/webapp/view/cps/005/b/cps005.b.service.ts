module nts.uk.com.view.cps005.b {
    export module service {
        export class Service {
            paths = {
                getAllPerInfoItemDefByCtgId: "ctx/pereg/person/info/ctgItem/findby/categoryId1/{0}/{1}",
                getPerInfoItemDefById: "ctx/pereg/person/info/ctgItem/findby/itemId/{0}/{1}",
                addItemDef: "ctx/pereg/person/info/ctgItem/add",
                updateItemDef: "ctx/pereg/person/info/ctgItem/update",
                removeItemDef: "ctx/pereg/person/info/ctgItem/remove",
                getAllSelectionItem: "ctx/pereg/person/info/setting/selection/findAllSelectionItem",
                filterHisSel: "ctx/pereg/person/info/setting/selection/findSelectionInit",
                checkItemData: "ctx/pereg/person/info/ctgItem/check/itemData/{0}"

            }

            constructor() {

            }

            getAllPerInfoItemDefByCtgId(categoryId: string,  personEmployeeType: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAllPerInfoItemDefByCtgId, categoryId, personEmployeeType);
                return nts.uk.request.ajax("com", _path);
            };

            getPerInfoItemDefById(itemId: string, personEmployeeType: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getPerInfoItemDefById, itemId, personEmployeeType);
                return nts.uk.request.ajax("com", _path);
            };

            getAllSelectionItem(): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.getAllSelectionItem);
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
            
            getAllSelByHistory(selectionItemId: string, selectionItemClsAtr : number) {
                let query = {
                    selectionItemId : selectionItemId, 
                    selectionItemClsAtr : selectionItemClsAtr, 
                    cps006 : false
                }
                return nts.uk.request.ajax(this.paths.filterHisSel, query);
            };
            
            checkItemData(itemId: string){
               return nts.uk.request.ajax(nts.uk.text.format(this.paths.checkItemData, itemId));
            };
        }
    }
}
