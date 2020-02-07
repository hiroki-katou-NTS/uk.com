module nts.uk.at.view.jmm018.c.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        dataRetirment: KnockoutObservableArray<any> = ko.observableArray([]);
        durationList: [];
        retirePlanCourseClassList: [];
        title: KnockoutObservable<string> = ko.observable("");
        constructor() {
            let self = this;
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            block.grayout();
            self.durationList = __viewContext.enums.DurationFlg;
            
            self.retirePlanCourseClassList = __viewContext.enums.RetirePlanCourseClass;
            
            let param = getShared('employmentTypeToC');
            self.title(param.commonMasterItemName);
            let tg = [];
            let listSorted = _.sortBy(param.listInfor, ['durationFlg', 'retirementAge']);
            _.forEach(listSorted, (obj) => {
                 
                let durationFind = _.find(self.durationList, function(item) {
                    return obj.durationFlg == item.value;
                });                
                let kt = _.find(param.listSelect, {'retirePlanCourseId': obj.retirePlanCourseId});
                
                let enterParam = {
                    retirePlanCourseId: obj.retirePlanCourseId,
                    // C222_5
                    checkBox: kt != undefined,
                    // C222_6
                    retirePlanCourseClass: _.find(self.retirePlanCourseClassList, { 'value': obj.retirePlanCourseClass}),
                    // C222_8
                    retirePlanCourseName: obj.retirePlanCourseName,
                    // C222_9
                    retirementAge: obj.retirementAge.toString() + ' ' + getText('JMM018_C222_16'),
                    // C222_10
                    durationFlg: durationFind.name,
                }
                tg.push(new RetirementCourse(enterParam));
            });   
            
            self.dataRetirment(tg);
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }


        // close dialog
        closeDialog() {
            nts.uk.ui.windows.close();
        }

        /** update or insert data when click button register **/
        register() {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            let listData = _.filter(self.dataRetirment(), function(o) { return o.checkBox() == true; });
            let normal = _.filter(listData, function(x) { return x.retirePlanCourseClass().value == 0; });
            let size = _.size(normal)
            if(size >= 2){
                nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_14"});
                block.clear();
            }else if(size == 0 || !size){
                nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_13"});
                block.clear();
            }else{
                setShared('shareToJMM018B', listData);
                block.clear();
                nts.uk.ui.windows.close();
            }
        }
}

    export interface IRetirementCourse {
        retirePlanCourseId: number;
        // C222_5
        checkBox: boolean;
        // C222_6
        retirePlanCourseClass: string;
        // C222_8
        retirePlanCourseName: string;
        // C222_9
        retirementAge: string;
        // C222_10
        durationFlg: string;
    }
    
    class RetirementCourse {
        retirePlanCourseId: number;
        // C222_5
        checkBox: KnockoutObservable<boolean>;
        // C222_6
        retirePlanCourseClass: KnockoutObservable<string>;
        // C222_8
        retirePlanCourseName: KnockoutObservable<string>;
        // C222_9
        retirementAge: KnockoutObservable<string>;
        // C222_10
        durationFlg: KnockoutObservable<string>;
        constructor(param: IRetirementCourse) {
            let self = this;
            self.retirePlanCourseId = param.retirePlanCourseId;
            self.checkBox = ko.observable(param.checkBox);
            self.retirePlanCourseClass = ko.observable(param.retirePlanCourseClass);
            self.retirePlanCourseName = ko.observable(param.retirePlanCourseName);
            self.retirementAge = ko.observable(param.retirementAge);
            self.durationFlg = ko.observable(param.durationFlg);
        }
    }

}