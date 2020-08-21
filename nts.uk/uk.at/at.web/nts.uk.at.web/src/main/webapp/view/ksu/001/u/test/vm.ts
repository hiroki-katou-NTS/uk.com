@bean()
class Test extends ko.ViewModel {  
    constructor(){
        super();
    }

    public openModal(): void {
        const vm = this,
             request = { baseDate: "2020/08/12",
                            unit: 1,
                            workplaceId: '2a08b71c-9e16-4e65-8e34-a78df6d67a01',
                            workplaceGroupId: '57a38034-3840-4443-93b2-cc323a4feff6'
                     };  
            // request = { baseDate: "2020/08/12",
            //                 unit: 1,
            //                 workplaceId: '9d68af4d-437d-4362-a118-65899039c38f',
            //                 workplaceGroupId: '7a44da55-e54a-4383-a304-3ed18455f1c4'
            //          };  
        vm.$window.modal("/view/ksu/001/u/index.xhtml", request);        
    }
}
