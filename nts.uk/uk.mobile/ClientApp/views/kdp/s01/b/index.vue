<template>
  <div>
    <div class="modal-header rounded-0 d-block p-0">
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-left mr-1" v-on:click="$close"></i>
          <span>{{ "KDPS01_5" | i18n }}</span>
        </h4>
      </div>
    </div>
     <div style="text-align: center;" >
      <div
        v-bind:style="{
          color: getTextColor(screenData.date)
        }"
        >{{ screenData.date | date("YYYY/MM/DD") }}</div
      >
      <div
        class="stamp-time font-weight-bold"
        style="line-height: 2.5rem;"
        >{{ screenData.date | date("HH:mm") }}</div
      >
      <div>
        <div class="d-inline-block" ></div>
        <label
          class="stamp-text font-weight-bold"
          style="line-height: 3rem; color:#0033cc;"
          >{{ screenData.stampAtr }}</label
        >
        </div>
    </div>
    <div class="col-12 value" style="padding-top: 20px; padding-bottom: 40px;">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
      <div>{{ screenData.localtion }}</div>
    </div>

     <label class="font-weight-bold col-12 value"> {{ "KDPS01_67" | i18n }}</label>

    <div class="col-12 value" style="padding-bottom: 40px;">
      <img tabindex="2" v-bind:src="'/nts.uk.mobile.web/dist/resources/185.png'" height="50" width="50"/>
      <img tabindex="3" v-bind:src="'/nts.uk.mobile.web/dist/resources/186.png'" height="50" width="50"/> 
      <img tabindex="4" v-bind:src="'/nts.uk.mobile.web/dist/resources/187.png'" height="50" width="50"/> 
      <img tabindex="5" v-bind:src="'/nts.uk.mobile.web/dist/resources/188.png'" height="50" width="50"/> 
      <img tabindex="6" v-bind:src="'/nts.uk.mobile.web/dist/resources/189.png'" height="50" width="50"/> 
    </div>

    <div class="col-12 value" v-if="params.resultDisplayTime > 0">
      <label class="cd-time font-weight-bold mr-2">{{
        params.resultDisplayTime
      }}</label>
      <label class="font-weight-bold">{{ "KDPS01_36" | i18n }}</label>
    </div>
    <div>
      <button
        tabindex="1"
        v-focus
        type="button"
        v-on:click="$close"
        class="btn-block mt-3 btn btn-secondary"
      >
        {{ "KDPS01_37" | i18n }}
      </button>
    </div>
  </div>
</template>
