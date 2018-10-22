module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    export class ScreenModel {
        isOnStartUp: boolean = true;
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        selectedWageTableIdentifier: KnockoutObservable<string> = ko.observable(null);
        wageTableList: any = ko.observableArray();
        constructor() {
            let self = this;
            for (let i = 0; i < 5 ; i ++ ){
                let child = [];
                for(let j = 0 ; j < 5 ; j ++){
                    child.push(new model.WageTableTreeNode('000' + i + nts.uk.util.randomId(), '', '', (201809-j) + '~' + (201809-j-1), []));
                }
                let node = new model.WageTableTreeNode('000' + i,'000' + i, 'サービス部', '000' + i +'サービス部', child);
                self.wageTableList.push(node);
            }
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        createNewWageTable () {
            nts.uk.ui.errors.clearAll();
        }
        registerWageTable () {

        }
        copy () {
            // TODO
        }
        outputExcel () {
            // TODO
        }
        settingQualificationGroup () {
            // TODO
        }
        correctMasterDialog () {
            // TODO
        }
    }
}

