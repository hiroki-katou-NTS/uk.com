<template>
  <div class="kafs08a1 mt-n2">
    <div class="step-wizard">
      <step-wizard v-bind:items="['KAFS08_10', 'KAFS08_11', 'KAFS08_12']" v-bind:selected="step" />
    </div>
    <kafs08-a2 v-if="!seen" v-bind:departureTime="departTureTime" v-bind:returnTime="returnTime"/>
    <div v-if="seen" class="mx-n2">
      <div>
        <kafs00-a v-if="kaf000_A_Params != null" v-bind:params="kaf000_A_Params" />
        <template v-else />
      </div>
      <!-- A2_1 -->
      <div class="card bg-danger top-alert uk-text-danger topError">
        <button class="btn btn-link uk-text-danger">
          <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
          {{ 'KAFS08_13' | i18n }}
        </button>
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
            <span>{{'KAFS08_27' | i18n}}</span>
            <span class="badge badge-info">任意</span>
          </div>
        </div>
        <!-- A5_3 -->
        <nts-time-editor
          v-model="departTureTime"
          :name="'KAFS08_28'"
          time-input-type="time-with-day"
          :columns="{ title: 'col-3', input: 'col-5' }"
        />
        <!-- A5_5 -->
        <nts-time-editor
          v-model="returnTime"
          :name="'KAFS08_29'"
          time-input-type="time-with-day"
          :columns="{ title: 'col-3', input: 'col-5' }"
        />
        <!-- A_20 -->
        <div>
          <kafs00-c v-if="kaf000_C_Params != null" v-bind:params="kaf000_C_Params" />
        </div>
        <!-- A50_F1 -->
        <div class="process-button">
          <!-- A50_1 -->
          <button
            @click="nextToStepTwo()"
            type="button"
            class="btn btn-success btn-block"
          >{{'KAFS08_31' | i18n}}</button>
        </div>
      </div>
    </div>
  </div>
</template>