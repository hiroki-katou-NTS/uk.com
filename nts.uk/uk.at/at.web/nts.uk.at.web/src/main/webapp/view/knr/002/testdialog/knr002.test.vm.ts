module knr002.test {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal =  nts.uk.ui.windows.sub.modal;



    export module viewmodel{
        export class ScreenModel{
       
            isMulti: boolean;
            sharedContent: KnockoutObservable<string>;

            //  B_Dialog
            empInfoTerCode_B: KnockoutObservable<string>;
            empInfoTerName_B: KnockoutObservable<string>;
            modelEmpInfoTerName_B: KnockoutObservable<string>;
            workLocationName_B: KnockoutObservable<string>;
            lastSuccessDate_B: KnockoutObservable<string>;
            status: KnockoutObservable<string>;
            //  D_Dialog
            empInfoTerCode_D: KnockoutObservable<string>;
            empInfoTerName_D: KnockoutObservable<string>;
            empInfoTerList_D: KnockoutObservableArray<any>;
            command_D: any;
            
            //currentCodeList_D: KnockoutObservableArray<any>;
            //  F_Dialog
            empInfoTerCode_F: KnockoutObservable<string>;
            empInfoTerName_F: KnockoutObservable<string>;
            modelEmpInfoTer_F: KnockoutObservable<number>;
            lastSuccessDate_F: KnockoutObservable<string>;
            empInfoTerList_F: KnockoutObservableArray<any>;
            //  G_Dialog
            empInfoTerCode_G: KnockoutObservable<string>;
            empInfoTerName_G: KnockoutObservable<string>;
            modelEmpInfoTerName_G: KnockoutObservable<string>;
            workLocationName_G: KnockoutObservable<string>;

            // H_Dialog
            companyId : KnockoutObservable<string> = ko.observable("");
           // empNo : KnockoutObservable<string> = ko.observable("");
            //  K_Dialog
            empInfoTerCode_K: KnockoutObservable<string>;
            
            constructor(){
                var self = this;
                self.sharedContent = ko.observable("The Shared Content is: \nScreen: ");
                self.isMulti = true;
                //B_Dialog
                self.empInfoTerCode_B = ko.observable("0001");
                self.empInfoTerName_B = ko.observable("Name 1B_Shared");
                self.modelEmpInfoTerName_B = ko.observable("NRL-1B");
                self.workLocationName_B = ko.observable("Work Location 1B");
                self.lastSuccessDate_B = ko.observable("2020/12/12 12:12:12");
                self.status = ko.observable("Normal");
                //D_Dialog
                self.empInfoTerCode_D = ko.observable("0001");
                self.empInfoTerName_D = ko.observable("Name D_Shared");
                self.empInfoTerList_D = ko.observableArray([new EmpInfoTerminal("0001", "Name1", "NRL-1", "WLN 1"), 
                                                            new EmpInfoTerminal("0002", "Name2", "NRL-2", "WLN 2"), 
                                                            new EmpInfoTerminal("0003", "Name3", "NRL-3", "WLN 3"), 
                                                            new EmpInfoTerminal("0004", "Name4", "NRL-4", "WLN 4"),
                                                            new EmpInfoTerminal("0005", "Name5", "NRL-5", "WLN 5")]);
                self.command_D = new D_Command("0001", "Name 1", "R1", 8, [new TimeRecordSetUpdateDto("v1", "u1")]);
                // self.currentCodeList_D = ko.observableArray([new ItemModel("0001"), 
                //                                              new ItemModel("0002"),
                //                                              new ItemModel("0005"),
                //                                              new ItemModel("0004")]);

                //F_Dialog
                self.empInfoTerCode_F = ko.observable("0001");
                self.empInfoTerName_F = ko.observable("Name F_Shared");
                self.modelEmpInfoTer_F = ko.observable(9);
                self.lastSuccessDate_F = ko.observable("2020/12/12 12:12:12");
                self.empInfoTerList_F = ko.observableArray([new EmpInfoTerminal("0001", "Name1", "NRL-1", "WLN 1"), 
                                                            new EmpInfoTerminal("0002", "Name2", "NRL-2", "WLN 2"), 
                                                            new EmpInfoTerminal("0003", "Name3", "NRL-3", "WLN 3"), 
                                                            new EmpInfoTerminal("0004", "Name4", "NRL-4", "WLN 4"),
                                                            new EmpInfoTerminal("0005", "Name5", "NRL-5", "WLN 5")]);


                // G_Dialog
                self.empInfoTerCode_G = ko.observable("0001");
                self.empInfoTerName_G = ko.observable("Name 2G_Shared");
                self.modelEmpInfoTerName_G = ko.observable("NRL-G");
                self.workLocationName_G = ko.observable("Work Location 2G");
                //  K_Dialog
                self.empInfoTerCode_K = ko.observable("0001");
            }

            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.clear(); 																			
                dfd.resolve();											
                return dfd.promise();											
            }    
            /**
             * 
             */
            private test_B_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002B_empInfoTerCode', self.empInfoTerCode_B());
                setShared('KNR002B_empInfoTerName', self.empInfoTerName_B());
                setShared('KNR002B_modelEmpInfoTer', self.modelEmpInfoTerName_B());
                setShared('KNR002B_workLocationName', self.workLocationName_B());
                setShared('KNR002B_lastSuccessDate', self.lastSuccessDate_B());
                setShared('KNR002B_status', self.status());
                modal('/view/knr/002/b/index.xhtml', { title: 'B_Screen', }).onClosed(() => {
                    // Do nothing
                    blockUI.clear();
                });
            }
            
            /**
             * 
             */
            private test_D_Dialog(): void{
                var self = this;
                blockUI.invisible();
                //setShared From C
                setShared('KNR002D_command', self.command_D);
                //setShare From A
                setShared('KNR002D_empInfoTerList', self.empInfoTerList_D());
                //setShared('KNR002D_currentCodeList', self.currentCodeList_D());

                modal('/view/knr/002/d/index.xhtml', { title: 'D_Screen', }).onClosed(() => {
                    // let getSharedLst = getShared('KNR002D_selectableCodeList');                  
                    // if(getSharedLst)
                    //     self.currentCodeList_D(getSharedLst);
                    blockUI.clear();
                });
            }

            /**
             * 
             */
            private test_F_Dialog(): void{
                var self = this;
                blockUI.invisible();
                // setShared from E
                setShared('KNR002F_empInfoTerCode', self.empInfoTerCode_F());
                setShared('KNR002F_empInfoTerName', self.empInfoTerName_F());
                setShared('KNR002F_modelEmpInfoTer', self.modelEmpInfoTer_F());
                setShared('KNR002F_lastSuccessDate', self.lastSuccessDate_F());
                // setShare from A
                setShared('KNR002F_empInfoTerList', self.empInfoTerList_F());
                modal('/view/knr/002/f/index.xhtml', { title: 'F_Screen', }).onClosed(() => {
                
                blockUI.clear();
                });
            }

            /**
             * 
             */
            private test_G_Dialog(): void{
                var self = this;
                blockUI.invisible();
                //setShared from A
                setShared('KNR002G_empInfoTerCode', self.empInfoTerCode_G());
                setShared('KNR002G_empInfoTerName', self.empInfoTerName_G());
                setShared('KNR002G_modelEmpInfoTer', self.modelEmpInfoTerName_G());
                setShared('KNR002G_workLocationName', self.workLocationName_G());

                modal('/view/knr/002/g/index.xhtml', { title: 'G_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
            
            public openKNR002GDialog(data: any) {
                const vm = this;
                blockUI.invisible();
                setShared('KNR002G_empInfoTerCode', data.empInfoTerCode);
                setShared('KNR002G_empInfoTerName', data.empInfoTerName);
                setShared('KNR002G_modelEmpInfoTer', data.displayModelEmpInfoTer);
                setShared('KNR002G_workLocationName', data.workLocationName);
       
                modal('/view/knr/002/g/index.xhtml', { title: 'G_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
            
            /**
             * 
             */
            private test_H_Dialog(): void{
                modal('/view/knr/002/h/index.xhtml', { title: 'H_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private resetEmps(): void{
                let self = this;
                if(!self.companyId())
                    return;
                 for(let i = 1; i <= 1000; i++){
                    let command: any = {
                            employeeName: "シュアン　　シュアン",
                            employeeCode: `knr${self.fillZeros(i)}`,
                            hireDate: "2021-05-18T00:00:00.000Z",
                            cardNo: "",
                            loginId: `KNR002${self.fillZeros(i)}`,
                            password: "0",
                            avatarOrgId: "",
                            avatarCropedId: "",
                            categoryName: "ファイル管理",
                            itemName: "顔写真",
                            fileName: "",
                            employeeCopyId: "",
                            initSettingId: "",
                            inputs: [
                                {
                                    categoryCd: "CS00002",
                                    categoryId: `${self.companyId()}00000000000_CS00002`,
                                    categoryName: "個人基本情報",
                                    categoryType: 1,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00002_IS00004`,
                                            itemCode: "IS00004",
                                            itemName: "個人名カナ",
                                            value: "シュアン　　シュアン",
                                            type: 1,
                                            logType: 1
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00002_IS00009`,
                                            itemCode: "IS00009",
                                            itemName: "表示氏名(ビジネスネーム)",
                                            value: "シュアン　　シュアン",
                                            defValue: "シュアン　　シュアン",
                                            type: 1,
                                            logType: 1
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00002_IS00017`,
                                            itemCode: "IS00017",
                                            itemName: "生年月日",
                                            value: "2021/05/03",
                                            type: 3,
                                            logType: 3
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00002_IS00018`,
                                            itemCode: "IS00018",
                                            itemName: "性別",
                                            text: "男性",
                                            value: "1",
                                            defText: "男性",
                                            defValue: "1",
                                            type: 2,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00014",
                                    categoryId: `${self.companyId()}00000000000_CS00014`,
                                    categoryName: "雇用",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00014_IS00068`,
                                            itemCode: "IS00068",
                                            itemName: "雇用CD",
                                            text: "99　<雇用>",
                                            value: "99",
                                            defText: "99　<雇用>",
                                            defValue: "99",
                                            type: 2,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00016",
                                    categoryId: `${self.companyId()}00000000000_CS00016`,
                                    categoryName: "職位",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00016_IS00079`,
                                            itemCode: "IS00079",
                                            itemName: "職位CD",
                                            text: "99999　<職位>",
                                            value: `JOB_ID_xxxxxxxxxxx_${self.companyId()}`,
                                            defText: "99999　<職位>",
                                            defValue: `JOB_ID_xxxxxxxxxxx_${self.companyId()}`,
                                            type: 1,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00017",
                                    categoryId: `${self.companyId()}00000000000_CS00017`,
                                    categoryName: "職場",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00017_IS00084`,
                                            itemCode: "IS00084",
                                            itemName: "職場CD",
                                            text: "000000000　0",
                                            value: "66c7f8bb-9a59-4594-a2ca-1eab4dcd4f83",
                                            type: 1,
                                            logType: 8
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00021",
                                    categoryId: `${self.companyId()}00000000000_CS00021`,
                                    categoryName: "勤務種別",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00021_IS00257`,
                                            itemCode: "IS00257",
                                            itemName: "勤務種別CD",
                                            text: "0000000001　<勤務種別>",
                                            value: "0000000001",
                                            defText: "0000000001　<勤務種別>",
                                            defValue: "0000000001",
                                            type: 1,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00004",
                                    categoryId: `${self.companyId()}00000000000_CS00004`,
                                    categoryName: "分類",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00004_IS00028`,
                                            itemCode: "IS00028",
                                            itemName: "分類CD",
                                            text: "9999999999　<分類>",
                                            value: "9999999999",
                                            defText: "9999999999　<分類>",
                                            defValue: "9999999999",
                                            type: 2,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00020",
                                    categoryId: `${self.companyId()}00000000000_CS00020`,
                                    categoryName: "労働条件",
                                    categoryType: 3,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00252`,
                                            itemCode: "IS00252",
                                            itemName: "就業区分",
                                            text: "通常勤務",
                                            value: "0",
                                            defText: "通常勤務",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00121`,
                                            itemCode: "IS00121",
                                            itemName: "スケジュール管理",
                                            text: "管理しない",
                                            value: "0",
                                            defText: "管理する",
                                            defValue: "1",
                                            type: 2,
                                            logType: 7
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00123`,
                                            itemCode: "IS00123",
                                            itemName: "スケジュール作成方法",
                                            text: "カレンダー",
                                            value: "0",
                                            defText: "カレンダー",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00124`,
                                            itemCode: "IS00124",
                                            itemName: "カレンダーの参照先",
                                            text: "職場",
                                            value: "0",
                                            defText: "職場",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00125`,
                                            itemCode: "IS00125",
                                            itemName: "基本勤務の参照先",
                                            text: "職場",
                                            value: "0",
                                            defText: "職場",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00127`,
                                            itemCode: "IS00127",
                                            itemName: "月間パターン",
                                            text: "001　<月間パターン>",
                                            value: "001",
                                            defText: "001　<月間パターン>",
                                            defValue: "001",
                                            type: 1,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00126`,
                                            itemCode: "IS00126",
                                            itemName: "就業時間帯の参照先",
                                            text: "マスタ参照区分に従う",
                                            value: "0",
                                            defText: "マスタ参照区分に従う",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00130`,
                                            itemCode: "IS00130",
                                            itemName: "平日の勤務種類",
                                            text: "001　<勤務種類１>",
                                            value: "001",
                                            type: 1,
                                            logType: 8
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00131`,
                                            itemCode: "IS00131",
                                            itemName: "平日の就業時間帯",
                                            text: "001　<就業時間帯>",
                                            value: "001",
                                            type: 1,
                                            logType: 8
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00133`,
                                            itemCode: "IS00133",
                                            itemName: "平日の開始時刻1",
                                            value: "480",
                                            type: 2,
                                            logType: 5
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00134`,
                                            itemCode: "IS00134",
                                            itemName: "平日の終了時刻1",
                                            value: "1020",
                                            type: 2,
                                            logType: 5
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00128`,
                                            itemCode: "IS00128",
                                            itemName: "休日の勤務種類",
                                            text: "002　<勤務種類２>",
                                            value: "002",
                                            type: 1,
                                            logType: 8
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00139`,
                                            itemCode: "IS00139",
                                            itemName: "休出の勤務種類",
                                            text: "001　<勤務種類１>",
                                            value: "001",
                                            type: 1,
                                            logType: 8
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00140`,
                                            itemCode: "IS00140",
                                            itemName: "休出の就業時間帯",
                                            text: "001　<就業時間帯>",
                                            value: "001",
                                            type: 1,
                                            logType: 8
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00142`,
                                            itemCode: "IS00142",
                                            itemName: "休出の開始時刻1",
                                            value: "480",
                                            type: 2,
                                            logType: 5
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00143`,
                                            itemCode: "IS00143",
                                            itemName: "休出の終了時刻1",
                                            value: "1020",
                                            type: 2,
                                            logType: 5
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00247`,
                                            itemCode: "IS00247",
                                            itemName: "休暇時の就業時間帯の自動設定",
                                            text: "しない",
                                            value: "0",
                                            defText: "しない",
                                            defValue: "0",
                                            type: 2,
                                            logType: 7
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00253`,
                                            itemCode: "IS00253",
                                            itemName: "契約時間",
                                            value: "480",
                                            type: 2,
                                            logType: 4
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00248`,
                                            itemCode: "IS00248",
                                            itemName: "休暇時の加算時間",
                                            text: "しない",
                                            value: "0",
                                            defText: "しない",
                                            defValue: "0",
                                            type: 2,
                                            logType: 7
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00249`,
                                            itemCode: "IS00249",
                                            itemName: "加算時間１日",
                                            type: 2,
                                            logType: 4
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00250`,
                                            itemCode: "IS00250",
                                            itemName: "加算時間午前",
                                            type: 2,
                                            logType: 4
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00251`,
                                            itemCode: "IS00251",
                                            itemName: "加算時間午後",
                                            type: 2,
                                            logType: 4
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00258`,
                                            itemCode: "IS00258",
                                            itemName: "勤務時間の自動設定",
                                            text: "しない",
                                            value: "0",
                                            defText: "しない",
                                            defValue: "0",
                                            type: 2,
                                            logType: 7
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00020_IS00259`,
                                            itemCode: "IS00259",
                                            itemName: "時給者区分",
                                            text: "時給者",
                                            value: "0",
                                            defText: "時給者",
                                            defValue: "0",
                                            type: 2,
                                            logType: 6
                                        }
                                    ]
                                },
                                {
                                    categoryCd: "CS00024",
                                    categoryId: `${self.companyId()}00000000000_CS00024`,
                                    categoryName: "年休情報",
                                    categoryType: 1,
                                    delete: false,
                                    items: [
                                        {
                                            definitionId: `${self.companyId()}000_CS00024_IS00279`,
                                            itemCode: "IS00279",
                                            itemName: "年休付与基準日",
                                            value: "2021/05/11",
                                            type: 3,
                                            logType: 3
                                        },
                                        {
                                            definitionId: `${self.companyId()}000_CS00024_IS00280`,
                                            itemCode: "IS00280",
                                            itemName: "年休付与テーブル",
                                            text: "<付与テーブル>",
                                            value: "01",
                                            defText: "<付与テーブル>",
                                            defValue: "01",
                                            type: 1,
                                            logType: 6
                                        }
                                    ]
                                }
                            ],
                            createType: 3
                        };

                    blockUI.invisible();
                    setTimeout(() => {
                        service.addNewEmployee(command).done(() => {
                            console.log("add emp: done!!!__", i);
                        }).fail(() => {
                            console.log("add emp: false!!!__", i);
                            return;
                        }).always(() => {
                            blockUI.clear();
                        });
                    }, 3000);
                }
            }

            /**
             * 
             */
            private test_K_Dialog(): void{
                var self = this;
                blockUI.invisible();
                 //setShared from G
                setShared('KNR002K_empInfoTerCode', self.empInfoTerCode_K());
                modal('/view/knr/002/k/index.xhtml', { title: 'K_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private fillZeros(i: number): string{
                let str : string = i.toString()
                return str.length == 1 ? `000${str}` : str.length == 2 ? `00${str}` : str.length == 3 ? `0${str}` : str;
            }
        }

        class D_Command {
            empInfoTerCode: any;
            empInfoTerName: string;
            romVersion: string;
            modelEmpInfoTer: number;
            listTimeRecordSetUpdateDto: Array<TimeRecordSetUpdateDto>;
            constructor(empInfoTerCode: any, empInfoTerName: string, romVersion: string, modelEmpInfoTer: number, listTimeRecordSetUpdateDto: Array<TimeRecordSetUpdateDto>){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.romVersion = romVersion;
                this.modelEmpInfoTer = modelEmpInfoTer;
                this.listTimeRecordSetUpdateDto = listTimeRecordSetUpdateDto;
            }
        }
        class TimeRecordSetUpdateDto{
            variableName: string;
            updateValue: string;
            constructor(variableName: string, updateValue: string){
                this.variableName = variableName;
                this.updateValue = updateValue;
            }
        }

        class EmpInfoTerminal{
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTerName: string;
            workLocationName: string;
            availability: boolean;
            constructor(empInfoTerCode: string, empInfoTerName: string, modelEmpInfoTerName: string, workLocationName: string){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.modelEmpInfoTerName = modelEmpInfoTerName;
                this.workLocationName = workLocationName;
                this.availability = false;
            }
        }  
        class ItemsByCategory {
           categoryId: string;
           categoryCd: string;          
           categoryName: string;           
           categoryType: number;
           recordId: string;
           delete: boolean;
           items: Array<ItemValueP>;
            constructor(categoryId: string,
                        categoryCd: string,
                        categoryName: string, 
                        categoryType: number, 
                        recordId: string, 
                        delet: boolean,
                        items: Array<ItemValueP>){
                this.categoryId = categoryId;
                this.categoryCd =  categoryCd;
                this.categoryName =  categoryName;     
                this.categoryType =  categoryType;
                this.recordId =  recordId;
                this.delete = delet;
                this.items = items;
            }
        }
        class ItemValueP{
           definitionId: string;
           itemCode: string;
           itemName: string;
           value: string;
           text: string;
           defValue: string;
           defText: string;
           type: number;
           logType: number;
            constructor(definitionId: string,
                                                itemCode: string,
                                                itemName: string,
                                                value: string,
                                                text: string,
                                                defValue: string, 
                                                defText: string, 
                                                type: number, 
                                                logType: number){
                this.definitionId = definitionId;
                this.itemCode = itemCode;
                this.itemName = itemName;
                this.value = value;
                this.text = text;
                this.defValue = defValue;
                this.defText = defText;
                this.type = type;
                this.logType = logType;
            }
        }

        class AddEmployeeCommand {
             employeeCopyId: string;
             initSettingId: string;
             employeeName: string;
             employeeCode: string;
             hireDate: string;
             cardNo: string;
             createType: number;
             password: string;
             loginId: string;
             avatarOrgId: string;
             avatarCropedId: string;
             categoryName: string;
             itemName: string;             
             fileName: string;            
            inputs: Array<ItemsByCategory>;
	
        }

    }
}