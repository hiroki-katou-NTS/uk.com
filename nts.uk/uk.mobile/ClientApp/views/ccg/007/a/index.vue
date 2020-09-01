<template>
  <div id="contract-confirm" style="width: 100%; height: 100%">
    <div class="text-center page-title uk-bg-olive">{{'CCGS07_50' | i18n}}</div>
    <div class="text-left page-info mt-3 uk-text-dark-gray">
      <div>{{ 'CCGS07_51' | i18n }}</div>
    </div>
    <fieldset class="login-form mt-3">
      <!-- <form v-submit="login"> -->
      <nts-text-editor
        id="contractCode"
        name="CCGS07_52"
        v-bind:tabindex="1"
        v-model="model.contractCode"
        v-bind:show-title="true"
        v-bind:columns="{ title: 'col-lg-4', input: 'col-lg-8' }"
        v-bind:constraint="validations.model.contractCode"
      />
      <nts-input-password
        id="password"
        name="CCGS07_53"
        v-bind:tabindex="2"
        v-model="model.password"
        v-bind:show-title="true"
        v-bind:columns="{ title: 'col-lg-4', input: 'col-lg-8' }"
        v-bind:constraint="validations.model.password"
      />
      <!-- </form> -->
      <div class="text-left page-info mt-3 uk-text-dark-gray">
        <div>{{ 'CCGS07_54' | i18n }}</div>
      </div>
      <div class="mt-3">
        <button
          v-bind:tabindex="3"
          class="btn btn-primary btn-block"
          v-click:500="authContract"
        >{{'CCGS07_55' | i18n}}</button>
      </div>
    </fieldset>
  </div>
</template>