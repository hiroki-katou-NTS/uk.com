__viewContext.ready(function() {

    class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            this.items = ko.observableArray([]).extend({ deferred: true });

            this.currentCode = ko.observable();
        }

        test() {
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("test" + i, '基本給', "description", "other"));
            }
            this.items(array);
        }

        test2() {
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel2("test" + i, '基本給', "description", "other"));
            }
            $("#test2").igGrid({
                dataSource: array,
                primaryKey: 'ID',
                width: '1000px',
                height: '500px',
                autoGenerateColumns: false,
                features: [{
                    name: 'Updating',
                    enableAddRow: false,
                    enableDeleteRow: false
                },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                columns: [
                    { headerText: 'ID', key: 'ID', dataType: 'string', width: 110 },
                    { headerText: 'Contact Name', key: 'ContactName', dataType: 'string', width: 210 },
                    { headerText: 'City', key: 'City', dataType: 'string', width: 120 },
                    { headerText: 'Country', key: 'Country', dataType: 'string', width: 120 }
                ]
            });
        }
    }

    class ItemModel {
        ID: KnockoutObservable<any>;
        ContactName: KnockoutObservable<string>;
        City: KnockoutObservable<string>;
        Country: KnockoutObservable<string>;
        constructor(ID: any, ContactName: string, City: string, Country?: string) {
            this.ID = ko.observable(ID).extend({ deferred: true });
            this.ContactName = ko.observable(ContactName).extend({ deferred: true });
            this.City = ko.observable(City).extend({ deferred: true });
            this.Country = ko.observable(Country).extend({ deferred: true });
        }
    }

    class ItemModel2 {
        ID: any;
        ContactName: string;
        City: string;
        Country: string;
        constructor(ID: any, ContactName: string, City: string, Country?: string) {
            this.ID = ID;
            this.ContactName = ContactName;
            this.City = City;
            this.Country = Country;
        }
    }
    ko.options.deferUpdates = true;
    var vmDeferred = new ScreenModel();
    this.bind(vmDeferred);
});