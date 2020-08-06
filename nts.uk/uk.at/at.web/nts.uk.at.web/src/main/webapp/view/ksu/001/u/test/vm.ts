@bean()
class Test extends ko.ViewModel {  
    constructor(){
        super();
    }

    public openDialog(): void {
        const vm = this;        
        vm.$window.modeless("/view/ksu/001/u/index.xhtml");
    }
}
