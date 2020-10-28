module nts.uk.com.view.cmm018.a.sub {
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    //=========Mode A: まとめて登録モード==============
    export module viewmodelSubA {
        export class ScreenModel{
            items: KnockoutObservableArray<any> = ko.observableArray([]);
            lstData: KnockoutObservableArray<vmbase.CompanyAppRootADto> = ko.observableArray([]);
            intervalDetectResolution: any;
            gridName: any;
            constructor() {  
            }
            // fix bug 109950
            scrollToIndex(object: any) {
                let index = 0;
                // not to use Array.findIndex in IE
                if (this.lstData() != null) {
                    for (let i = 0; i < this.lstData().length; ++i) {
                        if (this.lstData()[i].approvalId == object.approvalId) {
                            index = i;
                            break;
                        }
                    }
                }  
                $(this.gridName).igGrid("virtualScrollTo", index);

            }
            reloadGridN(lstRoot: Array<vmbase.CompanyAppRootADto>, rootType: vmbase.RootType, mode: vmbase.MODE): JQueryPromise<void>{
                var dfd = $.Deferred<void>();
                let self = this;
                let systemAtr = __viewContext.viewModel.viewmodelA.systemAtr();
                let width = 950;
                let height = 530;
                self.gridName = '#grid_matome';
                $('html.sidebar-html').css('overflow','visible');
                $('div#left-contents').css('margin-top',"0px"); 
                if(rootType == vmbase.RootType.COMPANY){
                    if(mode == vmbase.MODE.MATOME) {
                        self.gridName = '#grid_matome';        
                        if(systemAtr == 0) {
//                            width = screen.width - 435 > 950 ? 950 : screen.width - 435;
//                            width = (screen.width * 1000)/(1920);
//                            height = (screen.height * 450)/(1080 - 40);
                            //1920*1040
                            if (screen.height == 1080 && screen.width == 1920) {
                                    width = 950;
                                    height = 600;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
//                                     width = 750;
                                     width = 860
                                     height = 315;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
//                                width = 510;
                                width = 600;
                                height = 315;                           
                            }
                            
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
							if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                     height = 230;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
                                height = 230;                           
                            } 
                        }
                    } else {
                        self.gridName = '#grid_matomeB';   
                        if(systemAtr == 0) {
                            width = (screen.width * 900)/(1920);
                            height = (screen.height * 450)/(1080 - 40);  
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
                        }     
                    }
                }else if(rootType == vmbase.RootType.WORKPLACE){
                    if(mode == vmbase.MODE.MATOME) {
                        self.gridName = '#grid_matomeC';    
                        if(systemAtr == 0) {
                           if (screen.height == 1080 && screen.width == 1920) {
                                    width = 950;
                                    height = 600;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                     width = 850;
                                     height = 315;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
                                width = 600;
                                height = 315;                           
                            }
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
							if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                     height = 180;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
                                height = 180;                           
                            } 
                        }     
                    } else {
                        self.gridName = '#grid_matomeD'; 
                        if(systemAtr == 0) {
                            width = screen.width - 465 > 950 ? 950 : screen.width - 465;    
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
                        }       
                    }
                }else{//PERSON
                    if(mode == vmbase.MODE.MATOME) {
                        self.gridName = '#grid_matomeE';   
                        if(systemAtr == 0) {
                            
                           if (screen.height == 1080 && screen.width == 1920) {
                                width = 950;
                                height = 580;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                width = 800;
                                height = 285;
                                $('div#left-contents').css('margin-top',"-20px");
                               if(window.outerHeight == 728 && window.outerWidth == 1280){
                                        $('html.sidebar-html').css('overflow','hidden');
                                }  
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {
                                width = 550;
                                height = 285;
                                $('div#left-contents').css('margin-top',"-20px");
                               if(window.outerHeight == 728 && window.outerWidth == 1024){
                                        $('html.sidebar-html').css('overflow','hidden');
                               }  
                            }

                        } else {
                            width = screen.width - 195 > 950 ? 950 : screen.width - 195;
							if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                     height = 170;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
                                height = 170;                           
                            } 
                        }     
                    } else {
                        self.gridName = '#grid_matomeF';     
                        if(systemAtr == 0) {
                            width = screen.width - 520 > 950 ? 950 : screen.width - 520;    
                        } else {
                            width = screen.width - 195 > 950 ? 950 : screen.width - 195;
                        }   
                    }
                }
                if($(self.gridName + '_container').length > 0){
                    $(self.gridName).ntsGrid("destroy");
                }
                self.items([]);
                self.lstData(lstRoot);
                _.each(lstRoot, function(root){
                    self.items.push(self.convertlistRoot(root));
                });
                let colorBackGr: any = self.fillColorbackGr(lstRoot);
                let heightG = systemAtr == 1 ? 430 : 530;
                if(mode == vmbase.MODE.SHINSEI){
                    heightG = 181;
                }
                heightG = height;
            
              $(self.gridName).ntsGrid({
                width: width,
                height: heightG,
                dataSource: self.items(),
                primaryKey: 'typeRoot',
                rowVirtualization: true,
                virtualization: true,
                hidePrimaryKey: true,
                virtualizationMode: 'continuous',
                autoFitWindow: false,
                columns: [
                    { headerText: 'ID', key: 'typeRoot', dataType: 'string', ntsControl: "Label", width: "0px", hidden: true },
                    { headerText: getText('CMM018_24'), key: 'appName', dataType: 'string', width: '130px'},
                    { headerText: getText('CMM018_28'), 
                        group:[{ headerText: getText('CMM018_30'), key: 'phase1', dataType: 'string', width: '100px' },
                               { headerText: '⇐' + getText('CMM018_31'), key: 'phase2', dataType: 'string', width: '100px' },
                               { headerText: '⇐' + getText('CMM018_32'), key: 'phase3', dataType: 'string', width: '100px' },
                               { headerText: '⇐' + getText('CMM018_33'), key: 'phase4', dataType: 'string', width: '100px' },
                               { headerText: '⇐' + getText('CMM018_34'), key: 'phase5', dataType: 'string', width: '100px' }]
                    },
                    { headerText: getText('CMM018_95'), key: 'deleteRoot', dataType: 'string', width: '75px'}
                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
//                            columnKey: 'typeRoot', allowResizing: false, minimumWidth: 0,
                            columnKey: 'appName', allowResizing: false,
                            columnKey: 'phase1', allowResizing: false,
                            columnKey: 'phase2', allowResizing: false,
                            columnKey: 'phase3', allowResizing: false,
                            columnKey: 'phase4', allowResizing: false,
                            columnKey: 'phase5', allowResizing: false,
                            columnKey: 'deleteRoot', allowResizing: false
                        }],
                        columnResized: function(event, ui) {
                            if (ui.columnKey === "appName") {
                                
                                if (rootType == vmbase.RootType.COMPANY) {
//                                    company
                                    uk.localStorage.setItem("AppName_CMM018_A", ui.newWidth);
                                }
                                else if (rootType == vmbase.RootType.WORKPLACE) {
//                                    workplace
                                    uk.localStorage.setItem("AppName_CMM018_B", ui.newWidth);
                                    
                                }else {
//                                    person
                                    uk.localStorage.setItem("AppName_CMM018_C", ui.newWidth);
                                } 
                            }
                           
                        }
                    },
                    { name: "MultiColumnHeaders" }
                ],
                 ntsFeatures:[
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: colorBackGr
                    },
                 ],
                ntsControls: [{ name: 'Button', controlType: 'Button', enable: true }],
            });
                
            setTimeout(() => {
                let width;
                
                if (rootType == vmbase.RootType.COMPANY) {
    //                  company
                   width = uk.localStorage.getItem("AppName_CMM018_A");
                }
                else if (rootType == vmbase.RootType.WORKPLACE) {
                   width = uk.localStorage.getItem("AppName_CMM018_B");
                      
                }else {
                   width = uk.localStorage.getItem("AppName_CMM018_C");
                } 
                // set width column クリア #110014
                $(self.gridName).igGridResizing("resize", "deleteRoot", 75);
                if (width.isPresent()) {
                    $(self.gridName).igGridResizing("resize", "appName", Number(width.get()));
                }else {
                    // calculate width's appName that equals character number of text , 1 character = 15 px
                    let numberText = 0;
                    _.forEach(self.lstData(), (item, index) => {
                        if(item.appTypeName) {
                            numberText = item.appTypeName.length >= numberText ? (index == 0 ? item.appTypeName.length + 1 : item.appTypeName.length) : numberText;
                        }
                        
                    })
                    if(numberText == 0) {
                        $(self.gridName).igGridResizing("resize", "appName", 130);
                    }else if(numberText <= 20) {
                        $(self.gridName).igGridResizing("resize", "appName", 15*numberText);
                    }
                }
                
                _.forEach(["grid_matome", "grid_matomeA", "grid_matomeB", "grid_matomeC", "grid_matomeD", "grid_matomeE", "grid_matomeF"], gridName => { 
                    uk.localStorage.removeItem(request.location.current.rawUrl + "/" + gridName );
                });
            }, 0);
                
            $(self.gridName).on("click", ".button-delete", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.deleteRowSub(empRType, appType);
            });
            //Phase1
            $(self.gridName).on("click", ".openK_Phase1", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.openDialogKSub(1, empRType, appType);
            });
            $(self.gridName).on("click", ".openK_Phase2", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.openDialogKSub(2, empRType, appType);
            });
            $(self.gridName).on("click", ".openK_Phase3", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.openDialogKSub(3, empRType, appType);
            });
            $(self.gridName).on("click", ".openK_Phase4", function(evt, ui) {
                    console.log('Phase4');
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.openDialogKSub(4, empRType, appType);
            });
            $(self.gridName).on("click", ".openK_Phase5", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                let empRType = id.split("_")[0];
                let appType = id.split("_")[1];
                self.openDialogKSub(5, empRType, appType);
            });
                // fix bug  109946
            if (systemAtr == 0) {
                $(self.gridName).igGrid("option", "width", width);
                $(self.gridName).igGrid("option", "height", height);
                
            }
                
            $(window).resize(function() {
                  $('html.sidebar-html').css('overflow','visible');
                    
                 }); 
           clearInterval(this.intervalDetectResolution);      
           this.intervalDetectResolution = (function () {
                var width1 = screen.width,
                    height1 = screen.height;
                setInterval(function () {
                    if (screen.width !== width1 || screen.height !== height1) {
                        width1 = screen.width;
                        height1 = screen.height;
                        $(window).trigger('resolutionchange');
                          
//                        $('div#left-contents').css('margin-top',"0px");  
                    console.log('height, width: '+width1 +','+height1);
                    let width = 0;
                    let height = 0;
                    if(rootType == vmbase.RootType.COMPANY){
                    if(mode == vmbase.MODE.MATOME) {
                        self.gridName = '#grid_matome';        
                        if(systemAtr == 0) {
                            if (screen.height == 1080 && screen.width == 1920) {
                                    width = 950;
                                    height = 600;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
//                                     width = 750;
                                     width = 860
                                     height = 315;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
//                                width = 510;
                                width = 600;
                                height = 315;                           
                            }
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
							if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
                                     height = 230;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
                                height = 230;                           
                            } 
                        }
                    } else {
                        self.gridName = '#grid_matomeB';   
                        if(systemAtr == 0) {
                            if (screen.height == 1080 && screen.width == 1920) {
                                    width = 950;
                                    height = 600;
                            }
                            //1280 * 768
                            else if (screen.height == 768 && screen.width == 1280) {
//                                     width = 750;
                                     width = 750
                                     height = 315;
                            }
                            //1024*768
                            else if (screen.height == 768 && screen.width == 1024) {           
//                                width = 510;
                                width = 510;
                                height = 315;                           
                            }    
                        } else {
                            width = screen.width - 124 > 950 ? 950 : screen.width - 124;
							
                        }     
                    }
                    }else if(rootType == vmbase.RootType.WORKPLACE){
                        if(mode == vmbase.MODE.MATOME) {
                            self.gridName = '#grid_matomeC';    
                            if(systemAtr == 0) {
                               if (screen.height == 1080 && screen.width == 1920) {
                                        width = 950;
                                        height = 600;
                                }
                                //1280 * 768
                                else if (screen.height == 768 && screen.width == 1280) {
                                         width = 850;
                                         height = 315;
                                }
                                //1024*768
                                else if (screen.height == 768 && screen.width == 1024) {           
                                    width = 600;
                                    height = 315;                           
                                }
                            } else {
                                width = screen.width - 124 > 950 ? 950 : screen.width - 124;
								if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
	                            }
	                            //1280 * 768
	                            else if (screen.height == 768 && screen.width == 1280) {
	                                     height = 180;
	                            }
	                            //1024*768
	                            else if (screen.height == 768 && screen.width == 1024) {           
	                                height = 180;                           
	                            } 
                            }     
                        } else {
                            self.gridName = '#grid_matomeD'; 
                            if(systemAtr == 0) {
                                width = screen.width - 465 > 950 ? 950 : screen.width - 465;    
                            } else {
                                width = screen.width - 124 > 950 ? 950 : screen.width - 124;
                            }       
                        }
                    }else{//PERSON
                        if(mode == vmbase.MODE.MATOME) {
                            self.gridName = '#grid_matomeE';   
                            if(systemAtr == 0) {
                                if (screen.height == 1080 && screen.width == 1920) {
                                    width = 950;
                                    height = 580;
                                }
                                //1280 * 768
                                else if (screen.height == 768 && screen.width == 1280) {
                                    width = 800;
                                    height = 285;
                                    $('div#left-contents').css('margin-top',"-20px");
                                   if(window.outerHeight == 728 && window.outerWidth == 1280){
                                            $('html.sidebar-html').css('overflow','hidden');
                                    }  
                                }
                                //1024*768
                                else if (screen.height == 768 && screen.width == 1024) {
                                    width = 550;
                                    height = 285;
                                    $('div#left-contents').css('margin-top',"-20px");
                                   if(window.outerHeight == 728 && window.outerWidth == 1024){
                                            $('html.sidebar-html').css('overflow','hidden');
                                   }  
                                }       
                            } else {
                                width = screen.width - 195 > 950 ? 950 : screen.width - 195;
								if (screen.height == 1080 && screen.width == 1920) {
                                    height = 430;
	                            }
	                            //1280 * 768
	                            else if (screen.height == 768 && screen.width == 1280) {
	                                     height = 170;
	                            }
	                            //1024*768
	                            else if (screen.height == 768 && screen.width == 1024) {           
	                                height = 170;                           
	                            } 
                            }     
                        } else {
                            self.gridName = '#grid_matomeF';     
                            if(systemAtr == 0) {
                                width = screen.width - 520 > 950 ? 950 : screen.width - 520;    
                            } else {
                                width = screen.width - 195 > 950 ? 950 : screen.width - 195;
                            }   
                        }
                    }
                    if ($(self.gridName).igGrid("option", "width") != width){
                        $(self.gridName).igGrid("option", "width", width);
                    }
                        
                    if ($(self.gridName).igGrid("option", "height") != height){
                        $(self.gridName).igGrid("option", "height", height);
                    }          
                    }
                }, 50);
           }());   
            dfd.resolve();
            return dfd.promise();
        }
        resize1(){
            let self = this;
            let a = __viewContext.viewModel.viewmodelSubA.items();
            let width = self.cal(a[0].phase1);
            $("#grid_matome").igGridResizing("resize", 'phase1', width);

        }
        resize2(){
            let self = this;
            let a = __viewContext.viewModel.viewmodelSubA.items();
            let width = self.cal(a[0].phase2);
            $("#grid_matome").igGridResizing("resize", 'phase2', width);

        }
        resize3(){
            let self = this;
            let a = __viewContext.viewModel.viewmodelSubA.items();
            let width = self.cal(a[0].phase3);
            $("#grid_matome").igGridResizing("resize", 'phase3', width);

        }
        cal1(){
            let self = this;
            let a = __viewContext.viewModel.viewmodelSubA.items();
            let width = self.cal(a[0].phase1);
            alert(width);
        }
        cal(inputText) {
            let font = "14px DroidSansMono, Meiryo"; 
            let canvas = document.createElement("canvas"); 
            let context = canvas.getContext("2d"); 
            context.font = font; 
            let width = context.measureText(inputText).width; 
            let textPixel = Math.ceil(width); 
            let halfPixel = nts.uk.text.countHalf(inputText)* 8
            console.log(inputText);
            console.log(textPixel);
            console.log(halfPixel);
            console.log((textPixel + halfPixel)/2);
            return (textPixel + halfPixel)/2 + 5; 
        }  
        convertlistRoot(root: vmbase.CompanyAppRootADto): vmbase.Root{
            let self = this;
            let typeRoot: string = root.employRootAtr +'_'+ root.appTypeValue;
            let appName:string = self.appNameHtml(root.appTypeName, root.employRootAtr);
            let phase1:string = self.phaseHtml(root.appPhase1, root.appTypeValue, root.employRootAtr,1);
            let phase2:string = self.phaseHtml(root.appPhase2, root.appTypeValue, root.employRootAtr,2);
            let phase3:string = self.phaseHtml(root.appPhase3, root.appTypeValue, root.employRootAtr,3);
            let phase4:string = self.phaseHtml(root.appPhase4, root.appTypeValue, root.employRootAtr,4);
            let phase5:string = self.phaseHtml(root.appPhase5, root.appTypeValue, root.employRootAtr,5);
            let deleteRoot: string = '<div class="td-delete"><button class="button-delete"></button></div>'; 
            return {typeRoot: typeRoot,
                            appName:appName,
                            phase1:phase1,
                            phase2:phase2,
                            phase3:phase3,
                            phase4:phase4,
                            phase5:phase5,
                            deleteRoot: deleteRoot};
        }
         
        phaseHtml(phase: vmbase.ApprovalPhaseDto, appTypeValue, employRootAtr, phaseOrder: number):any{
            let classPhase = 'openK_Phase' + phaseOrder;
            if(_.isEmpty(phase.approver) || phase.approver.length == 0){//phase chua setting
                return '<div class="hyperlink approver-line ' + classPhase + '"><span>' + getText('CMM018_99') + '</span></div>';
            }
            let result = '<div class="approver">';
            _.each(phase.approver, function(approver){
                result += '<div class="hyperlink approver-line ' + classPhase + '"><span>' + approver.name + approver.confirmName +'</span></div>';
            });
                result += '</div>'+ '<div class="from">' + '（' + phase.appFormName + '）' + '</div>';
           return result;
            
        }
        openDialogKSub(phaseOrder: number, empRType: any, appType: any){
            let self = this;
            let root: vmbase.CompanyAppRootADto = self.findRoot(empRType, appType);
            let phase = null;
            if(_.isEmpty(root)) return;
            if(phaseOrder == 1) phase = root.appPhase1;
            if(phaseOrder == 2) phase = root.appPhase2;
            if(phaseOrder == 3) phase = root.appPhase3;
            if(phaseOrder == 4) phase = root.appPhase4;
            if(phaseOrder == 5) phase = root.appPhase5;
            __viewContext.viewModel.viewmodelA.openDialogK(phase, root.approvalId, appType, empRType, phaseOrder);
        }
        findRoot(empRType: any, appType: any): vmbase.CompanyAppRootADto{
            let self = this;
            if(Number(empRType) == 0){//common
                return _.find(self.lstData(), function(root){
                    return root.employRootAtr == 0;
                });
            }
            return _.find(self.lstData(), function(root){//event
                return root.employRootAtr == Number(empRType) && root.appTypeValue == appType;
            });
        }
        fillColorbackGr(lstRoot): Array<vmbase.CellState>{
            let self = this;
            let result = [];
            _.each(lstRoot, function(root) {
                let rowId = root.employRootAtr +'_'+ root.appTypeValue;
                result.push(new vmbase.CellState(rowId,'appName',['appNameBgColor']));
                result.push(new vmbase.CellState(rowId,'deleteRoot',['phaseBgColorNot']));
                if(!_.isEmpty(root.appPhase1.approver) && root.appPhase1.approver.length > 0){
                    result.push(new vmbase.CellState(rowId,'phase1',['phaseBgColor']));
                }else{
                    result.push(new vmbase.CellState(rowId,'phase1',['phaseBgColorNot']));
                }
                if(!_.isEmpty(root.appPhase2.approver) && root.appPhase2.approver.length > 0){
                    result.push(new vmbase.CellState(rowId,'phase2',['phaseBgColor']));
                }else{
                    result.push(new vmbase.CellState(rowId,'phase2',['phaseBgColorNot']));
                }
                if(!_.isEmpty(root.appPhase3.approver) && root.appPhase3.approver.length > 0){
                    result.push(new vmbase.CellState(rowId,'phase3',['phaseBgColor']));
                }else{
                    result.push(new vmbase.CellState(rowId,'phase3',['phaseBgColorNot']));
                }
                if(!_.isEmpty(root.appPhase4.approver) && root.appPhase4.approver.length > 0){
                    result.push(new vmbase.CellState(rowId,'phase4',['phaseBgColor']));
                }else{
                    result.push(new vmbase.CellState(rowId,'phase4',['phaseBgColorNot']));
                }
                if(!_.isEmpty(root.appPhase5.approver) && root.appPhase5.approver.length > 0){
                    result.push(new vmbase.CellState(rowId,'phase5',['phaseBgColor']));
                }else{
                    result.push(new vmbase.CellState(rowId,'phase5',['phaseBgColorNot']));
                }
            });
            return result;
        }
        
            appNameHtml(appName: string, empRType: number){
                if(empRType != 0) return appName;
                return appName + '<div style="display: inline-block;">'
                    + '<button class="help-button-custom">?</button></div>';
            }
            deleteRowSub(empRType: any, appType: any){
                let self = this;
                let root: vmbase.CompanyAppRootADto = self.findRoot(empRType, appType);
                if(_.isEmpty(root)) return;
                __viewContext.viewModel.viewmodelA.deleteRow(root.approvalId, empRType);
            }
        }
    }
}