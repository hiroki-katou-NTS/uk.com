module nts.uk.com.view.cas013.a.viewmodel {
    export class ScreenModel {
        //ComboBOx RollType
        listRollType: KnockoutObservableArray<RollType>;
        selectedRoleType: KnockoutObservable<number>;
        //ComboBox Company

        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<RoleIndividual>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //Date time picker
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.listRollType = ko.observableArray([]);
            self.selectedRoleType = ko.observable(1);
            this.listRoleIndividual = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                this.listRoleIndividual.push(new RoleIndividual('00' + i, '基本給', "description " + i, i % 3 === 0));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 },
                { headerText: '説明', key: 'description', width: 150 }
            ]);
            self.currentCode = ko.observable();
            //Date time picker
            self.startDate = ko.observable('');
            self.endDate = ko.observable('');

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAllRoleIndividualCom().done(function(data) {
                console.log(data);
                self.listRollType(data.enumRoleType);
                dfd.resolve();
            });

            return dfd.promise();
        }
        openCAS012_B() {
            let self = this
            nts.uk.ui.windows.sub.modal("/view/cas/012/b/index.xhtml").onClosed(() => {

            });
        }
        openCAS012_C() {
            let self = this
            nts.uk.ui.windows.sub.modal("/view/cas/012/c/index.xhtml").onClosed(() => {
                let returnDataScreenC = nts.uk.ui.windows.getShared("ReturnData");
            });
        }

    }


    class RoleIndividual {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;

        }
    }
    export interface Screen {

        roleType: number;
        companyID: string;
        userID: string;
    }
}

