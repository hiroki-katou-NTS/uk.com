/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.cmm004.a.screenModel {

  const API = {
    save: 'ctx/sys/gateway/singlesignon/saml/saveAuthSetting',
    operation: 'ctx/sys/gateway/login/sso/saml/operation/find',
    validation: 'ctx/sys/gateway/login/sso/saml/validation/find'
  };

  @bean()
  export class ViewModel extends ko.ViewModel {

    useSingleSignOn: KnockoutObservable<boolean> = ko.observable(false);
    idpRedirectUrl: KnockoutObservable<string> = ko.observable('');

    clientId: KnockoutObservable<string> = ko.observable('');
    idpEntityId: KnockoutObservable<string> = ko.observable('');
    idpCertificate: KnockoutObservable<string> = ko.observable('');
    operation: SamlOperation = new SamlOperation();
    validation: SamlResponseValidation = new SamlResponseValidation();

    created() {
      const vm = this;
      vm
        .$blockui('grayout')
        .then(() => vm.initScreen())
        .always(() => vm.$blockui('clear'));
    }

    initScreen(): JQueryPromise<any> {
      const vm = this;
      const dfd = $.Deferred();
      $
        .when(vm.$ajax('com', API.operation), vm.$ajax('com', API.validation))
        .then((operation: SamlOperation, validation: SamlResponseValidation) => {
          
          if (!!operation) {
            vm.operation = operation;
            vm.useSingleSignOn(operation.useSingleSignOn);
            vm.idpRedirectUrl(operation.idpRedirectUrl);
          }

          if (!!validation) {
            vm.validation = validation;
            vm.clientId(validation.clientId);
            vm.idpEntityId(validation.idpEntityId);
            vm.idpCertificate(validation.idpCertificate);
          }
          dfd.resolve();
        })
        .fail(() => dfd.reject());
      
      return dfd.promise();
    }

    mounted() {
      const vm = this;
      $('#useSingleSignOn').focus();
      vm.useSingleSignOn.subscribe(value => {
        if (!value) {
          vm.$errors('clear');
          return;
        }
        vm.$validate();
      })
    }

    register() {
      const vm = this;
      const handler = () => {
        const command = {
          useSingleSignOn: vm.useSingleSignOn(),
          idpRedirectUrl: vm.useSingleSignOn() ? vm.idpRedirectUrl() : vm.operation.idpRedirectUrl,
          clientId: vm.useSingleSignOn() ? vm.clientId() : vm.validation.clientId,
          idpEntityId: vm.useSingleSignOn() ? vm.idpEntityId() : vm.validation.idpEntityId,
          idpCertificate: vm.useSingleSignOn() ? vm.idpCertificate() : vm.validation.idpCertificate,
        };
  
        vm
          .$blockui('grayout')
          .then(() => vm.$ajax('com', API.save, command))
          .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
          .then(() => vm.initScreen())
          .always(() => vm.$blockui('clear'));
      }
      
      if (!vm.useSingleSignOn()) {
        handler();
        return;
      }

      vm.$validate().then((valid) => {
        if (!valid) return;
        handler();
      });
      
    }
  }

  class SamlOperation {
    useSingleSignOn: boolean = false;
    idpRedirectUrl: string = '';
  }

  class SamlResponseValidation {
    clientId: string = '';
    idpEntityId: string = '';
    idpCertificate: string ='';
  }
}
