json.pocketmons do
    json.array!(@pocketmons) do |pocketmon|
        json.extract! pocketmon, :id, :code, :latitude, :longitude, :level
        json.url pocketmon_url(pocketmon, format: :json)
    end
end    
