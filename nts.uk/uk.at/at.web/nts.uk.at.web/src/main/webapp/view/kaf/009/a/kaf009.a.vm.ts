module nts.uk.at.view.kaf009.b.viewmodel {
    export class ScreenModel {
        // date editor
        date: KnockoutObservable<string>;
        //time editor
        time: KnockoutObservable<string>;
        //check late
        late: KnockoutObservable<boolean>;
        late2: KnockoutObservable<boolean>;
        //check early
        early: KnockoutObservable<boolean>;
        early2: KnockoutObservable<boolean>;
        //labor time
        laborTime: KnockoutObservable<string>;
        //combobox
        ListLydo: KnockoutObservableArray<Lydo>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        //MultilineEditor
        multilineeditor: any;
        //Switch Button 
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //Back Home
        backHomeAtr: KnockoutObservableArray<any>;
        selectedBack: any;
        //Go Work
        goWorkAtr: KnockoutObservableArray<any>;
        selectedGo: any;
        //Back Home 2
        selectedBack2: any;
        //Go Work 2
        selectedGo2: any;
        constructor() {
            var self = this;
            //date editor
            self.date = ko.observable(nts.uk.resource.getText("KAF009_12"));
            //time editor
            self.time = ko.observable("12:00");
            //check late
            self.late = ko.observable(true);
            self.late2 = ko.observable(true);
            // check early
            self.early = ko.observable(false);
            self.early2 = ko.observable(false);
            //labor time 
            self.laborTime = ko.observable("00:00");
            //combobox
            self.ListLydo = ko.observableArray([
                new Lydo('1','name1'),
                new Lydo('2','name2'),
                new Lydo('3','name3')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0002');
            //Switch Button
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KAF009_9") },
                { code: '2', name: nts.uk.resource.getText("KAF009_10") }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //Go Work
            self.goWorkAtr = ko.observableArray([
            { goCode:'1',goName:nts.uk.resource.getText("KAF009_16")},
            { goCode:'2',goName:nts.uk.resource.getText("KAF009_17")}
            ]); 
            self.selectedGo = ko.observable(1);
            //BackHome 
            self.backHomeAtr = ko.observableArray([
            { backCode:'1',backName:nts.uk.resource.getText("KAF009_18")},
            { backCode:'2',backName:nts.uk.resource.getText("KAF009_19")}
            ]); 
            self.selectedBack = ko.observable(1);
            //Go Work 2
            self.selectedGo2 = ko.observable(1);
            //BackHome 2
            self.selectedBack2 = ko.observable(1);
            
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
    class Lydo {
        code: string; 
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}