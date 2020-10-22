/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.i {
  import getText = nts.uk.resource.getText;
  const MAXIMUM_IMAGE_COUNT = 4;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    //Choose file
    imageOption: ItemModel[] = [
      { code: 0, name: getText('CCG034_121') },
      { code: 1, name: getText('CCG034_123') }
    ];
    imageType: KnockoutObservable<number> = ko.observable(0);
    imageSrc: KnockoutObservable<string> = ko.observable('');
    imageList: ItemModel[] = [];
    // Upload file
    uploadedFileName: KnockoutObservable<string> = ko.observable('');
    fileSize: KnockoutObservable<number> = ko.observable(0);
    displayFileSize: KnockoutObservable<string> = ko.computed(() => {
      const vm = this;
      return nts.uk.text.format(getText("CCG034_125"), vm.fileSize());
    });
    fileId: KnockoutObservable<string> = ko.observable('');
    uploadSrc: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      
    }

    mounted() {
      const vm = this;
      vm.createPopUp();
    }

    upload() {
      const vm = this;
      $("#I2_2_1").ntsFileUpload('avatarfile').done(function (res: any) {
        vm.fileId(res[0].id);
      }).fail(function (err: any) {
        vm.$dialog.error({ messageId: err });
      });
    }

    uploadFinished(data: any) {
      const vm = this;
      console.log(data);
      vm.fileId(data.id);
      var liveviewcontainer = $("#I2_2_2");
      liveviewcontainer.html("");
      liveviewcontainer.append($("<img class='pic-preview'/>").attr("src", (nts.uk.request as any).liveView(vm.fileId())));
    }

    createPopUp() {
      const vm = this;
      // Generate image list
      for (let i = 0; i < 40; i++) {
        vm.imageList.push({ code: i, name: "../resource/CCG034I/CCG034I_" + nts.uk.text.padLeft(String(i+1), '0', 3) + ".png" });
      }
      // Adding images inside popup
      for (let i = 0; i < 40; i += MAXIMUM_IMAGE_COUNT) {
        let toAppend = "";
        for (let j = i; j < i + MAXIMUM_IMAGE_COUNT; j++) {
          toAppend += `<img id="I2_2_1_${j}" src="${vm.imageList[j].name}" class="pic-choose" data-bind="click: chooseImage" />`;
        }
        const template = `<div>
                            ${toAppend}
                          </div>`;
        $("#I2_4").append(template);
        // Rebind Knockout for the newly added div
        ko.applyBindings(vm, $("#I2_4 > div:last-child")[0]);
      }
      
      $("#I2_4").ntsPopup({
        trigger: "#I2_1_1",
        position: {
          my: "left top",
          at: "left bottom",
          of: "#I2_1_1"
        },
        showOnStart: false,
        dismissible: true
      });
    }

    chooseImage(data: any, event: any) {
      const vm = this;
      const itemId: string = event.currentTarget.id;
      const item = vm.imageList[Number(itemId.substr(7))];
      vm.imageSrc(item.name);
      $("#I2_4").ntsPopup("hide");
    }

    /**
    * Close dialog
    */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export interface ItemModel {
    code: number;
    name: string;
  }
}