module nts.uk.com.view.ccg009.a.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        columns: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCode: KnockoutObservable<number> = ko.observable(1);
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<any>;
        currentAlarm: KnockoutObservable<TopPageAlarmSet>;
        textSearch: string = nts.uk.resource.getText('CCG009_4') + nts.uk.resource.getText('で検索…');

        constructor() {
            let self = this;

            self.init();
            self.currentCode.subscribe((value) => {
                let item = _.find(ko.toJS(self.items()), (item) => {
                    return item.value == value;
                });
                self.currentAlarm(new TopPageAlarmSetDto(item.value, item.name, { alarmCategory: item.alarmCategory, useAtr: item.useAtr }));
                document.getElementById("single-list_container").focus();
            });
        }

        init() {
            let self = this;

            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('CCG009_4'), key: 'value', width: 0, formatter: _.escape, hidden: true },
                { headerText: nts.uk.resource.getText('CCG009_4'), key: 'name', width: 270, formatter: _.escape },
                { headerText: nts.uk.resource.getText('CCG009_5'), key: 'icon', width: 30 }
            ]);

            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('CCG009_8') },
                { code: '0', name: nts.uk.resource.getText('CCG009_9') }
            ]);
            self.selectedRuleCode = ko.observable(1);

            self.currentAlarm = ko.observable(new TopPageAlarmSetDto(
                0,
                '',
                {
                    alarmCategory: 0,
                    useAtr: 0
                }
            ));

        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.grayout();
            self.getAppType().done(function(){
                if (self.items().length) {
                    let item: TopPageAlarmSetDto = ko.toJS(self.items()[0]);
                    self.currentAlarm(new TopPageAlarmSetDto(item.value, item.name, { alarmCategory: item.alarmCategory, useAtr: item.useAtr }));
                }
                dfd.resolve();
            }).always(function() {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }



        //Get IdentityProcess 本人確認処理の利用設定
        getAppType(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.find().done((lstData: Array<ITopPageAlarmSet>) => {
                let listAlarmCategory = __viewContext.enums.AlarmCategory;
                _.forEach(listAlarmCategory, (item) => {
                    _.forEach(lstData, (obj) => {
                        if (item.value == obj.alarmCategory) {
                            self.items.push(new TopPageAlarmSetDto(item.value, item.name, obj));
                        }
                    });
                });
                dfd.resolve();
            })
            return dfd.promise();
        }
        // when click register button 
        saveData() {
            let self = this;
            let listUpdate = [];
            nts.uk.ui.block.grayout();
            // mapping to command
            let cmd = {
                alarmCategory: self.currentAlarm().alarmCategory(),
                useAtr: self.currentAlarm().useAtr()
            }
            if (nts.uk.ui.errors.hasError() === false) {
                let i = cmd.alarmCategory;
                service.update(cmd).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                        document.getElementById("single-list_container").focus();
                    });
                    self.items([]);
                    self.getAppType().done(function(){
                        let item: TopPageAlarmSetDto = ko.toJS(_.find(self.items(), (obj) => {
                            return obj.alarmCategory() == i;    
                        }));
                        self.currentAlarm(new TopPageAlarmSetDto(item.value, item.name, { alarmCategory: item.alarmCategory, useAtr: item.useAtr }));
                        
                    });
                }).always(function(){
                    nts.uk.ui.block.clear();    
                });
            }

        }
    }
    
    interface ITopPageAlarmSet {
        alarmCategory: number;
        useAtr: number;
    }
    class TopPageAlarmSetDto {
        value: number;
        name: string;
        alarmCategory: KnockoutObservable<number>;
        useAtr: KnockoutObservable<number>;
        icon: string;
        constructor(value: number, name: string, param: ITopPageAlarmSet) {
            let self = this;
            self.alarmCategory = ko.observable(param.alarmCategory);
            self.useAtr = ko.observable(param.useAtr);
            self.value = value;
            self.name = name;
            if(param.useAtr == 0){
                this.icon = "";
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
        }
    }

    class ItemModel {
        value: number;
        name: string;

        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }
}
