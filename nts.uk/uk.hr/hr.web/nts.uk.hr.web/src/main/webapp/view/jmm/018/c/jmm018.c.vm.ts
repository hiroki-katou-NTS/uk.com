module nts.uk.at.view.jmm018.c.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        dataRetirment: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            let pa = {
                index: 1,
                // C222_5
                checkBox: true,
                // C222_6
                courseAtr: "通常",
                // C222_8
                retirement: "b",
                // C222_9
                retirementAge: "c",
                // C222_10
                continuationCategory: "d",
            }
            
            let ram = {
                index: 2,
                // C222_5
                checkBox: false,
                // C222_6
                courseAtr: "通常",
                // C222_8
                retirement: "b",
                // C222_9
                retirementAge: "c",
                // C222_10
                continuationCategory: "d",
            }
            
            let a = {
                index: 1,
                // C222_5
                checkBox: false,
                // C222_6
                courseAtr: "通常",
                // C222_8
                retirement: "b",
                // C222_9
                retirementAge: "c",
                // C222_10
                continuationCategory: "d",
            }
            self.dataRetirment().push(new RetirementCourse(pa));
            self.dataRetirment().push(new RetirementCourse(ram));
            self.dataRetirment().push(new RetirementCourse(a));
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
            let listData = _.filter(self.dataRetirment(), function(o) { return o.checkBox() == true; });
            let normal = _.countBy(listData, function(x) { return x.courseAtr() == "通常"; });
            if(normal.true >= 2){
                nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_14"});
            }else if(normal.true == 0 || !normal.true){
                nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_13"});
            }else{
                nts.uk.ui.windows.setShared('shareToJMM018B', listData);
                nts.uk.ui.windows.close();   
            }
        }
}

    export interface IRetirementCourse {
        index: number;
        // C222_5
        checkBox: boolean;
        // C222_6
        courseAtr: string;
        // C222_8
        retirement: string;
        // C222_9
        retirementAge: string;
        // C222_10
        continuationCategory: string;
    }
    
    class RetirementCourse {
        index: number;
        // C222_5
        checkBox: KnockoutObservable<boolean>;
        // C222_6
        courseAtr: KnockoutObservable<string>;
        // C222_8
        retirement: KnockoutObservable<string>;
        // C222_9
        retirementAge: KnockoutObservable<string>;
        // C222_10
        continuationCategory: KnockoutObservable<string>;
        constructor(param: IRetirementCourse) {
            let self = this;
            self.index = param.index;
            self.checkBox = ko.observable(param.checkBox);
            self.courseAtr = ko.observable(param.courseAtr);
            self.retirement = ko.observable(param.retirement);
            self.retirementAge = ko.observable(param.retirementAge);
            self.continuationCategory = ko.observable(param.continuationCategory);
        }
    }
}