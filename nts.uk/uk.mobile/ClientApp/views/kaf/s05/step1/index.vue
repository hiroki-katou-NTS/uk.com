<template>
  <div class="kafs05step1">
    <div>
      <kafs00-a
        v-if="$appContext.kaf000_A_Params != null"
        v-bind:params="$appContext.kaf000_A_Params"
      />
    </div>
    <div
      v-if="!$appContext.$valid || !$appContext.isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS07_1" | i18n }}
      </button>
    </div>
    <div>
      <kafs00-b
        v-if="$appContext.kaf000_B_Params != null"
        v-bind:params="$appContext.kaf000_B_Params"
        v-on:kaf000BChangeDate="$appContext.kaf000BChangeDate"
        v-on:kaf000BChangePrePost="$appContext.kaf000BChangePrePost"
      />
    </div>
    <!-- Work type and work time -->
    <div v-if="true" class="card card-label">
      <!-- A4 -->
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS09_3" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
        <!-- A4_1 -->
        <span class="textSize uk-text-dark-gray">{{ "KAFS09_4" | i18n }}</span>
        <button type="button" class="btn btn-selection mt-2 mb-2">
          <!-- A4_2_1 -->
          <span class="badge badge-secondary">{{workInfo.workType.code}}</span>
          <span>{{workInfo.workType.name}}</span>
        </button>

        <!-- A4_3 -->
        <span class="textSize uk-text-dark-gray">{{ "KAFS09_5" | i18n }}</span>
        <button
          type="button"
          v-bind:enable="true"
          class="btn btn-selection mt-2 mb-2"
        >
          <!-- A4_3_1 -->
          <span class="badge badge-secondary">{{workInfo.workTime.code}}</span>
          <span>{{workInfo.workTime.name}}</span>
          <!-- A4_3_2 -->
          <span class="d-block mt-1">{{workInfo.workTime.time}}</span>
        </button>
      </div>
    </div>
    <!-- Work hours -->
    <div class="card card-label">
      <!-- A7_1 -->
      <div v-if="true" class="card-header uk-bg-accordion">
        <span>{{ "KAFS07_6" | i18n }}</span>
        <span class="badge badge-warning">任意</span>
      </div>
      <!-- A7_2 -->
      <div v-if="true" class="card-body">
        <nts-time-range-input v-if="true" v-model="workHours1" />
        <nts-time-range-input v-else disabled v-model="workHours1" />
      </div>
    </div>

    <div class="card card-label">
      <!-- A7_1 -->
      <div v-if="true" class="card-header uk-bg-accordion">
        <span>{{ "KAFS07_7" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>
      <!-- A7_2 -->
      <div v-if="true" class="card-body">
        <nts-time-range-input v-if="true" v-model="workHours2" />
        <nts-time-range-input v-else disabled v-model="workHours2" />
      </div>
    </div>

    <!-- Break -->
    <div class="card card-label">
      <div class="card-header uk-bg-accordion mt-2 mb-n2">
        <span>{{ "KAFS02_11" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>

      <!-- Break Hour -->
      <div class="card-body">
        <div class="row mt-3">
          <div class="col-6">Name's frame</div>
          <!-- <kafs00subp1 
                v-bind:params="kafS00P1Params1"
                /> -->
        </div>
        <div class="card-body">
          <nts-time-range-input class="mb-1" v-model="valueWorkHours1" />
        </div>
      </div>
      <!-- Add more frame zone -->
      <div v-if="true">
        <div class="text-center position-relative" style="height: 35px">
          <!-- A5_7 -->
          <div class="position-absolute w-100">
            <hr />
          </div>
          <!-- A5_6 -->
          <div class="position-absolute w-100 mt-1">
            <span
              class="fas fa-2x fa-plus-circle"
              style="color: #33b5e5"
            ></span>
          </div>
        </div>
        <div class="text-center">{{ "KAFS02_10" | i18n }}</div>
      </div>
    </div>

    <!-- A1_C1_1 -->
    <button
      type="button"
      class="btn btn-block btn-success btn-lg text-center"
      v-if="true"
      v-on:click="$appContext.toStep(2)"
    >
      {{ "KAFS02_17" | i18n }}
    </button>
  </div>
</template>