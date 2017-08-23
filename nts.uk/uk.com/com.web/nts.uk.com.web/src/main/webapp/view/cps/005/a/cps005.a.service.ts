module nts.uk.com.view.cps005.a {
    export module service {
        export class Service {
            paths = {
                getAllPerInfoCtg: "ctx/bs/person/info/category/findAll/company/root",
                getPerInfoCtg: "ctx/bs/person/info/category/find/companyby/{0}",
                getPerInfoCtgWithItemsName: "ctx/bs/person/info/category/find/withItemsName/{0}",
                addPerInfoCtg: "ctx/bs/person/info/category/add",
                updatePerInfoCtg: "ctx/bs/person/info/category/update",
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

            addPerInfoCtg(newCategory: any): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.addPerInfoCtg, newCategory);
            };

            updatePerInfoCtg(updateCategory: any): JQueryPromise<any> {
                return nts.uk.request.ajax("com", this.paths.updatePerInfoCtg, updateCategory);
            };

        }
    }
}
