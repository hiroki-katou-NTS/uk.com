<template>
<div class="kafs05step2">
    <!-- Errro-->
    

    <!-- OverTime -->
    <div v-if="true" class="card card-label">
      <div class="card-header uk-bg-accordion mt-2 mb-n2">
        <span>{{ "KAFS05_70" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div v-for="(item, index) in overTimes"
          v-bind:key="index"
          :value="index"
      >
        <div class="card-body">
          <div class="row mt-3">
            <div class="col-3">{{item.title}}</div>
            <div class="col-5">
              <kafs00subp1 
                    v-bind:params=item.preApp
                    />
            </div>
            <div class="col-3.5">
              <kafs00subp1 
                    v-bind:params=item.actualApp
                    />
            </div>    
          </div>
          <div class="card-body"> 
              <nts-time-editor
                v-model= item.applicationTime
                name=""
                require= true
                showTitle= true
                time-input-type="time-duration"
              />
          </div>
        </div>
      </div>
    </div>

    <!-- HolidayTime -->

    <div v-if="true" class="card card-label">
      <div class="card-header uk-bg-accordion mt-2 mb-n2">
        <span>{{ "KAFS05_73" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div v-for="(item, index) in holidayTimes"
          v-bind:key="index"
          :value="index"
      >
        <div class="card-body">
          <div class="row mt-3">
            <div class="col-3">{{item.title}}</div>
            <div class="col-5">
              <kafs00subp1 
                    v-bind:params=item.preApp
                    />
            </div>
            <div class="col-3.5">
              <kafs00subp1 
                    v-bind:params=item.actualApp
                    />
            </div>    
          </div>
          <div class="card-body"> 
              <nts-time-editor
                v-model= item.applicationTime
                name=""
                require= true
                showTitle= true
                time-input-type="time-duration"
              />
          </div>
        </div>
      </div>
    </div>


    <div class="card card-label">
        <!-- A2_C1_1 -->
        <button
      type="button"
      class="btn btn-block btn-primary btn-lg"
      v-if="true"
    >
      {{nameInsert}}
    </button>
    <!-- A2_C1_2 -->
    <button
      type="button"
      class="btn btn-block btn-secondary"
      v-if="true"
      v-on:click="backStep1"
    >
      {{ "KAFS05_12" | i18n }}
    </button>
    </div>
</div>
</template>