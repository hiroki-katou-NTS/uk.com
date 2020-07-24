module nts.uk.at.view.kaf007_ref.c.viewmodel {
    //import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange; 
    
    @component({
        name: 'kaf007-b',
        template: '/nts.uk.at.web/view/kaf_ref/007/b/index.html'
    })
    class Kaf007CViewModel extends ko.ViewModel {
        
        appDispInfoStartupOutput: any;
        
        created(
            params: { 
                appDispInfoStartupOutput: any, 
                event: (evt: () => void ) => void 
            }
        ) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = ko.observable(new Application("", 1, [], 2, "", "", 0));
            vm.appWorkChange = ko.observable(new AppWorkChange("001", "001", 100, 200));
            
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.event(vm.update.bind(vm));
        }
    
        mounted() {
            const vm = this;
        }
        
        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            
            console.log('update component', vm);    
        }
        
        dispose() {
            const vm = this;
            
        }
    }
    
    const API = {
        getWorkchangeByAppID_PC: "at/request/application/workchange/getWorkchangeByAppID_PC"
    }
}