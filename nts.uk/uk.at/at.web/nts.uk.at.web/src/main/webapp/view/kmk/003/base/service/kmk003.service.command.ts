module nts.uk.at.view.kmk003.a {
    import fixedset = nts.uk.at.view.kmk003.a.service.model.fixedset;
    import flowset = nts.uk.at.view.kmk003.a.service.model.flowset;
    import difftimeset = nts.uk.at.view.kmk003.a.service.model.difftimeset;
    import flexset = nts.uk.at.view.kmk003.a.service.model.flexset;
    import predset = nts.uk.at.view.kmk003.a.service.model.predset;
    import worktimeset = nts.uk.at.view.kmk003.a.service.model.worktimeset;
    
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    
    export module service {
        export module model {
            export module command {

                export interface WorkTimeCommonSaveCommand {
                    addMode: boolean;
                    predseting: predset.PredetemineTimeSettingDto;
                    worktimeSetting: worktimeset.WorkTimeSettingDto;
                }
                
                export interface FixedWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {
                    fixedWorkSetting: fixedset.FixedWorkSettingDto;
                    screenMode: TabMode;
                }
                
                export interface DiffTimeWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {
                    difftimeWorkSetting: difftimeset.DiffTimeWorkSettingDto;
                    screenMode: TabMode;
                }
                
                export interface FlowWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {
                    flowWorkSetting: flowset.FlWorkSettingDto;
                    screenMode: TabMode;
                }
                
                export interface FlexWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {
                    flexWorkSetting: flexset.FlexWorkSettingDto;
                    screenMode: TabMode;
                }
            }
        }
    }
}