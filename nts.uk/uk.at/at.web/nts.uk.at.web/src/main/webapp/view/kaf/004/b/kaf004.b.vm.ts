module nts.uk.at.view.kaf004.b.viewmodel {
    export class ScreenModel {
        // date editor
        date: KnockoutObservable<string>;
        //latetime editor
        lateTime1: KnockoutObservable<string>;
        lateTime2: KnockoutObservable<string>;
        //check send mail
        sendMail: KnockoutObservable<boolean>;
        //check late
        late1: KnockoutObservable<boolean>;
        late2: KnockoutObservable<boolean>;
        //check early
        early1: KnockoutObservable<boolean>;
        early2: KnockoutObservable<boolean>;
        //labor time
        earlyTime1: KnockoutObservable<string>;
        earlyTime2: KnockoutObservable<string>;
        //combobox
        ListTypicalReason: KnockoutObservableArray<TypicalReason>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        //MultilineEditor
        multilineeditor: any;

        constructor() {
            var self = this;
            //check sendMail
            self.sendMail = ko.observable(true);
            //date editor
            self.date = ko.observable("2017/01/08");
            //time editor
            self.lateTime1 = ko.observable("12:00");
            self.lateTime2 = ko.observable("12:30");
            //check late
            self.late1 = ko.observable(true);
            self.late2 = ko.observable(true);
            // check early
            self.early1 = ko.observable(false);
            self.early2 = ko.observable(false);
            //labor time 
            self.earlyTime1 = ko.observable("5:00"); 
            self.earlyTime2 = ko.observable("6:00");
            //combobox
            self.ListTypicalReason = ko.observableArray([
                new TypicalReason('1','name1'),
                new TypicalReason('2','name2'),
                new TypicalReason('3','name3')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3); 
            self.selectedCode = ko.observable('0002')
            //MultilineEditor 
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: false,
                    placeholder: "Placeholder for text editor",
                    width: "500",
                    textalign: "left"
                })),
            };

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
    }
   
    }
    class LateOrLeaveEarly{
        applicant: string;
        appDate : string;
        late1 : string;
        lateTime1 : string;
        early1 : string;
        earlyTime1 : string;
        late2 : string;
        lateTime2 : string;
        early2 : string;
        earlyTime2 : string;
        typeReason : string;
        appReason : string;
        
        
        

        
    }
}