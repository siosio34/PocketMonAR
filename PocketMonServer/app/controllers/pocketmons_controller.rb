class PocketmonsController < ApplicationController
  before_action :set_pocketmon, only: [:show, :edit, :update, :destroy]

  # GET /pocketmons
  # GET /pocketmons.json
  def index
    @pocketmons = Pocketmon.all
  end

  # GET /pocketmons/1
  # GET /pocketmons/1.json
  def show
  end


  # GET /pocketmons/1/edit
  def edit
  end

  
  def findpocket
    @latitude = params[:lat]
    @longitude = params[:log]
    # 경도 위도를 받아온 다음에 PocketMonList 에서 그거를 탐색
    @pocketmon_list = Pocketmon.where(latitude: (@latitude.to_f-0.5)..(@latitude.to_f+0.5), longitude: (@longitude.to_f-0.5)..(@longitude.to_f+0.5))      
  end

  def makepocket
    @new_pocketmon = Pocketmon.new
    @new_pocketmon[code] = params[:code]
    @new_pocketmon[latitude] = params[:latitude]
    @new_pocketmon[longitude] = params[:longitude]
    @new_pocketmon[level] = params[:level]

    if @new_pocketmon.save
        #성공적으로 저장되었을때
    else
        # 성공적으로 저장하지 못했을때
    end
  end  

  # GET /sellers/new
  def new
    @pocketmon = Pocketmon.new
  end

  # POST /pocketmons
  # POST /pocketmons.json
  def create
    @pocketmon = Pocketmon.new(pocketmon_params)

    respond_to do |format|
      if @pocketmon.save
        format.html { redirect_to @pocketmon, notice: 'Pocketmon was successfully created.' }
        format.json { render :show, status: :created, location: @pocketmon, :root => 'pocketmons' }
      else
        format.html { render :new }
        format.json { render json: @pocketmon.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /pocketmons/1
  # PATCH/PUT /pocketmons/1.json
  def update
    respond_to do |format|
      if @pocketmon.update(pocketmon_params)
        format.html { redirect_to @pocketmon, notice: 'Pocketmon was successfully updated.' }
        format.json { render :show, status: :ok, location: @pocketmon }
      else
        format.html { render :edit }
        format.json { render json: @pocketmon.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /pocketmons/1
  # DELETE /pocketmons/1.json
  def destroy
    @pocketmon.destroy
    respond_to do |format|
      format.html { redirect_to pocketmons_url, notice: 'Pocketmon was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_pocketmon
      @pocketmon = Pocketmon.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def pocketmon_params
      params.require(:pocketmon).permit(:code, :latitude, :longitude, :level)
    end
end
