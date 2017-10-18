module cps001.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        items: Array<GridItem> = [];
        comboItems = [new ItemModel('1', '基本給'),
            new ItemModel('2', '役職手当'),
            new ItemModel('3', '基本給2')];

        comboColumns = [{ prop: 'code', length: 4 },
            { prop: 'name', length: 8 }];


        constructor() {
            let self = this,
                dto: IModelDto = getShared('CPS001B_PARAM') || {};
            self.start();
            console.log(self.items);
            $("#grid2").ntsGrid('option', 'dataSource', self.items);

        }

        start() {
            let self = this;
            for (let i = 0; i < 5; i++) {
                self.items.push(new GridItem(i));
            }

        }
        pushData() {
            let self = this;

            setShared('CPS001B_VALUE', {});
            self.close();
        }
        
        clickLink() {
             alert('Do something 2'); 
        }
        close() {
            close();
        }
    }

    interface IModelDto {

    }

    class GridItem {
        id: number;
        header2: string;
        flag: boolean;
        ruleCode: string;
        combo: string;
        constructor(index: number) {
            this.id = index;
            this.header2 = index.toString();
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}