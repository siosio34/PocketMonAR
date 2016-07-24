json.array!(@pocketmon_list) do |pocketmon|
    json.extract! pocketmon, :id, :code, :latitude, :longitude, :level
    json.url getrangepocketmons_url(pocketmon, format: :json)
end    
