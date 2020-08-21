<template>
  <div class="kafs08a1">
    <div class="step-wizard">
      <step-wizard v-bind:items="['step_1', 'step_2', 'step_3']" v-bind:selected="step" />
    </div>
    <div>
           <kafs00-a v-if="kaf000_A_Params != null" v-bind:params="kaf000_A_Params" /> 
           <template v-else /> 
    </div>
    <!-- A2_1 -->
    <div class="alert error">
      <img
        class="iconWarn"
        src="https://www.iconsdb.com/icons/preview/red/warning-xxl.png"
        width="16"
        height="16"
      />
      <div class="contentError">{{'KAFS08_13'| i18n}}</div>
    </div>
    <div>
      <kafs00-b v-if="kaf000_B_Params != null" v-bind:params="kaf000_B_Params" />
      <template v-else /> 
    </div>
    <!-- A5 -->
    <div class="field-set">
      <!-- A5_1 -->
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{'#KAFS08_27' | i18n}}</span>
          <span class="badge badge-info">出 発</span>
        </div>
      </div>
      <div class="aParent">
        <!-- A5_2 -->
        <label class="uk-text-dark-gray">{{'KAFS08_28' | i18n}}</label>
        <!-- A5_3 -->
        <nts-time-editor
          v-model="timeWithDay"
          name="日区分付き時刻"
          time-input-type="time-with-day"
          v-bind:show-title="false"
        />
      </div>
      <div class="bParent">
        <!-- A5_4 -->
        <label class="uk-text-dark-gray">{{'KAFS08_29' | i18n}}</label>
        <!-- A5_5 -->
        <nts-time-editor
          v-model="timeWithDay"
          name="日区分付き時刻"
          time-input-type="time-with-day"
          v-bind:show-title="false"
        />
      </div>
      /dđsđsdsd
      <!-- A_20 -->
      <div>
        <kafs00-c
          v-if="kaf000_C_Params != null"
          v-bind:params="kaf000_C_Params" 
        />
      </div>
      <!-- A50_F1 -->
      <div class="process-button">
        <!-- A50_1 -->
        <button
          type="button"
          class="btn btn-success btn-block"
        >{{'KAFS08_31' | i18n}}</button>
      </div>
    </div>
  </div>
</template>