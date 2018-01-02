module nts.uk.at.view.kdw006.d.viewmodel {
    export class ScreenModelD {
        registeredTotalTimeCheer: KnockoutObservable<boolean>;
        completeDisplayOneMonth: KnockoutObservable<boolean>;
        useWorkDetail: KnockoutObservable<boolean>;
        registerActualExceed: KnockoutObservable<boolean>;
        displayConfirmMessage: KnockoutObservable<boolean>;
        useSupervisorConfirm: KnockoutObservable<boolean>;
        supervisorConfirmError: KnockoutObservable<number>;
        useConfirmByYourself: KnockoutObservable<boolean>;
        yourselfConfirmError: KnockoutObservable<number>;

        itemList: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            self.registeredTotalTimeCheer = ko.observable(false);
            self.completeDisplayOneMonth = ko.observable(false);
            self.useWorkDetail = ko.observable(false);
            self.registerActualExceed = ko.observable(false);
            self.displayConfirmMessage = ko.observable(false);
            self.useSupervisorConfirm = ko.observable(false);
            self.supervisorConfirmError = ko.observable(null);
            self.useConfirmByYourself = ko.observable(false);
            self.yourselfConfirmError = ko.observable(null);

            self.itemList = ko.observableArray([
                new BoxModel(0, 'エラーがあっても確認できる'),
                new BoxModel(1, 'エラーを訂正するまでチェックできない'),
                new BoxModel(2, 'エラーを訂正するまで登録できない')
            ]);
        }


        start(): JQueryPromise<any> {
            let self = this;
            return self.getFuncRestric();
        }

        //Get Function Restriction
        getFuncRestric(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getFuncRestric().done(function(data) {
                self.registeredTotalTimeCheer(data.registeredTotalTimeCheer);
                self.completeDisplayOneMonth(data.completeDisplayOneMonth);
                self.useWorkDetail(data.useWorkDetail);
                self.registerActualExceed(data.registerActualExceed);
                self.displayConfirmMessage(data.displayConfirmMessage);
                self.useSupervisorConfirm(data.useSupervisorConfirm);
                self.supervisorConfirmError(data.supervisorConfirmError);
                self.useConfirmByYourself(data.useConfirmByYourself);
                self.yourselfConfirmError(data.yourselfConfirmError);
                dfd.resolve();
            });
            return dfd.promise();
        }

        saveData() {
            let self = this;
            let funcRestric = {
                registeredTotalTimeCheer: self.registeredTotalTimeCheer(),
                completeDisplayOneMonth: self.completeDisplayOneMonth(),
                useWorkDetail: self.useWorkDetail(),
                registerActualExceed: self.registerActualExceed(),
                displayConfirmMessage: self.displayConfirmMessage(),
                useSupervisorConfirm: self.useSupervisorConfirm(),
                supervisorConfirmError: self.supervisorConfirmError(),
                useConfirmByYourself: self.useConfirmByYourself(),
                yourselfConfirmError: self.yourselfConfirmError()
            };
            service.update(funcRestric).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
        }

    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}
