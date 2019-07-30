module nts.uk.com.view.cps013.a.viewmodel {
    import block = nts.uk.ui.block;
    import request = nts.uk.request;
    import character = nts.uk.characteristics;
    import text = nts.uk.resource.getText;
    
    export class ScreenModel {
        date : KnockoutObservable<string> = ko.observable(null);
        items: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        // A2_001
        perInfoChk: KnockoutObservable<boolean> = ko.observable(false);
        // A3_001
        masterChk: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            // tạo list A3_004
            for(let i = 0; i < 7; i++){
                let item : IGridItem = {
                    id: i+1,
                    flag: false,
                    name: "",
                }
                let param = new GridItem(item);
                this.items.push(param);
            }
            self.items()[0].name = text("CPS013_15");
            self.items()[1].name = text("CPS013_16");
            self.items()[2].name = text("CPS013_17");
            self.items()[3].name = text("CPS013_18");
            self.items()[4].name = text("CPS013_19");
            self.items()[5].name = text("CPS013_20");
            self.items()[6].name = text("CPS013_21");
        }

        /** get data to list **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
                dfd.resolve();
                dfd.reject();
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            $("#grid2").ntsGrid({
                width: '300px',
                height: '230px',
                dataSource: self.items(),
                primaryKey: 'id',
                virtualization: true,
                columns: [
                    { headerText: '', key: 'id', dataType: 'number', width: '40px' },
                    { headerText: '', key: 'flag', dataType: 'boolean', width: '40px', ntsControl: 'Checkbox', showHeaderCheckbox: true },
                    { headerText: text("CPS013_14"), key: 'name', dataType: 'string', width: '220px' },
                ],
                features: [],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }],
            });
            character.restore("PerInfoValidCheckCtg").done((obj) => {
                $('#date_text').focus();
                if(obj){
                    self.date(obj.dateTime);
                    self.perInfoChk(obj.perInfoChk);
                    self.masterChk(obj.masterChk);
                    $("#grid2").igGrid("option","dataSource",obj.confirmTarget);
                    // khi tất cả check box được check thì thi load lên sẽ phải check cả check box trên header
                    let flag = _.countBy(ko.toJS($("#grid2").igGrid("option","dataSource")), function (x) { return x.flag == true; });
                    if(flag.true === 7){
                        $("#grid2_flag > span > div > label > input[type='checkbox']")[0].checked = true;
                    }else{
                        $("#grid2_flag > span > div > label > input[type='checkbox']")[0].checked = false;
                    }
                }

            }).fail(()=>{
                block.clear();    
            });

                dfd.resolve();
            return dfd.promise();
        }
        
        /** click button execute **/
        execute() {
            block.invisible();
            let self = this;
            
            let paramSave = {
                // A1_004
                dateTime: self.date(),
                // A2_001
                perInfoChk: self.perInfoChk(),
                // A3_001
                masterChk: self.masterChk(),
                confirmTarget: ko.toJS($("#grid2").igGrid("option","dataSource"))
            }
            character.save('PerInfoValidCheckCtg', paramSave);
            character.restore("PerInfoValidCheckCtg").done((obj) => {
            });
            let checkbox = ko.toJS($("#grid2").igGrid("option","dataSource")),
                checkDataFromUI = { 
                dateTime: self.date(),
                perInfoCheck: self.perInfoChk(),
                masterCheck: self.masterChk(),
                scheduleMngCheck: checkbox[0].flag,
                dailyPerforMngCheckL: checkbox[1].flag ,
                monthPerforMngCheck: checkbox[2].flag ,
                payRollMngCheck: checkbox[3].flag ,
                bonusMngCheck: checkbox[4].flag ,
                yearlyMngCheck: checkbox[5].flag ,
                monthCalCheck: checkbox[6].flag },
                flag = _.countBy(ko.toJS(checkbox), function(x) { return x.flag == true; });
            // nếu A2_001 và A3_001 cùng không được chọn hoặc A3_001 được chọn nhưng list A3_004 không được chọn item nào => msg_360
            if ((flag.true === 0 && self.masterChk() === false) || (self.masterChk() === false && self.perInfoChk() === false)) {
                nts.uk.ui.dialog.error({ messageId: "Msg_929" });
                block.clear();
                return;
            }
            service.checkHasCtg(checkDataFromUI).done((data  : IDataResult) =>{
                if (data.listCtg) {
                    checkDataFromUI.peopleCount = data.peopleCount;
                    checkDataFromUI.startTime   = data.startDateTime;
                    nts.uk.ui.windows.setShared('CPS013B_PARAMS', checkDataFromUI);
                    nts.uk.ui.windows.sub.modal('/view/cps/013/e/index.xhtml').onClosed(() => {
                        block.clear();
                    });
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_930" });
                    return;
                }
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            }).always(()=>block.clear());
        }
        

        /** remove item from list **/
        remove() {
        }
    }
    
    export interface IGridItem{
        id: number;
        flag: boolean;
        name: string;
    }
    
    class GridItem {
        id: number;
        flag: boolean;
        name: string;
        constructor(index: GridItem) {
            this.id = index.id;
            this.flag = index.flag;
            this.name = index.name;
        }
    }
    
    interface IDataResult{
        listCtg : Array<ICategoryInfo>;
        peopleCount: number;
        startDateTime: Date;
        endDateTime: Date;
    }
    
    interface ICategoryInfo{
        personInfoCategoryId : string;
        categoryCode: string;
        categoryName: string;
    }

}




