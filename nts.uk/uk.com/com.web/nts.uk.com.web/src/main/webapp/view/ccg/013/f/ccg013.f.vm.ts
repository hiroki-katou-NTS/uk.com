module nts.uk.com.view.ccg013.f.viewmodel {

    export class ScreenModel {
        //date editor
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        date: KnockoutObservable<string>;
        time: KnockoutObservable<string>;
        yearmontheditor: any;
        //list
        listStandardMenu: KnockoutObservableArray<StandardMenu>;
        list: KnockoutObservableArray<StandardMenu>;
        columns: Array<any>;
        features: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        constructor() {
           var self = this;
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            
            self.date = ko.observable("1990/01/01");
            self.time = ko.observable("12:00");
            // YearMonth Editor
            self.yearmontheditor = {
                value: ko.observable(200001),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                    inputFormat: 'yearmonth'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            
            //list
            self.listStandardMenu = ko.observableArray([]);
            self.list = ko.observableArray([]);
            self.columns = [
                { headerText: nts.uk.resource.getText("CCG013_51"), key: 'code', width: 80 },
                { headerText: nts.uk.resource.getText("CCG013_52"), key: 'targetItems', width: 150 },
                {
                    headerText: nts.uk.resource.getText("CCG013_83"), key: 'displayName', width: 150,
                    template: "<input class=\"displayName-input\" type=\"text\" value=\"${displayName}\" onchange='update(this, \"displayName\")' />"
                }
            ];
            self.features = ko.observableArray([
                {
                    name: 'Updating',
                    enableAddRow: true,
                    editMode: 'row',
                    columnSettings: [
                        {
                            columnKey: "displayName",
                            editorType: "text",
                            editorOptions: {
                                buttonType: "dropdown",
                                listItems: names,
                                readOnly: false
                            }
                        }
                    ]
                }
            ]);
        }

        // get data number "value" in list
        getListStandardMenu(value) {
            
        }

        // get data when start dialog
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            /** Get List StandrdMenu*/
        }
        
        // update data when click button register
        register() {
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
}
